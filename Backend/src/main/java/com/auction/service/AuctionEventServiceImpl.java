package com.auction.service;

import com.auction.exception.AuctionEventNotFoundException;
import com.auction.model.AuctionAction;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.User;
import com.auction.model.enums.AuctionStatus;
import com.auction.model.enums.AuctionType;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionActionRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.AuctionEventSortRepository;
import com.auction.repository.AuctionWinnerRepository;
import com.auction.repository.specification.AuctionEventSpecification;
import com.auction.repository.specification.SearchCriteria;
import com.auction.service.interfaces.AuctionChatService;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.AuctionWinnerService;
import com.auction.service.interfaces.MailService;
import com.auction.service.interfaces.NotificationSenderService;
import com.auction.service.interfaces.PaymentService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.request.AuctionEventRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
class AuctionEventServiceImpl implements AuctionEventService {

    private final AuctionEventRepository auctionEventRepository;
    private final AuctionWinnerRepository auctionWinnerRepository;
    private final AuctionActionRepository auctionActionRepository;
    private final AuctionWinnerService auctionWinnerService;
    private final NotificationSenderService notificationSenderService;
    private final AuctionEventSortRepository auctionEventSortRepository;
    private final UserService userService;
    private final MailService mailService;
    private final AuctionChatService auctionChatService;
    private final Mapper<AuctionEvent, AuctionEventDto> auctionEventToDtoMapper;
    private final PaymentService paymentService;

    private void checkDateForAuction(AuctionEventRequest request) {
        if(request.getStartDate().isBefore(LocalDateTime.now())) {
            throw new DateTimeException("Start date should be before" + LocalDateTime.now());
        }
        else if(request.getFinishDate().isBefore(LocalDateTime.now())) {
            throw new DateTimeException("Finish date should be before" + LocalDateTime.now());
        }

        if(request.getStartDate().isAfter(request.getFinishDate())) {
            throw new DateTimeException("Start date should be before Finish date!");
        }
    }

    @Override
    @Transactional
    public AuctionEventDto save(AuctionEventRequest request) {
        checkDateForAuction(request);

        User user = userService.findById(request.getUserId());
        AuctionEvent auctionEvent = AuctionEvent.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .user(user)
                .startPrice(request.getStartPrice())
                .finishPrice(request.getFinishPrice())
                .statusType(AuctionStatus.EXPECTATION)
                .startDate(request.getStartDate())
                .finishDate(request.getFinishDate())
                .genDate(LocalDateTime.now())
        .build();

        if (request.getCharityPercent() == 0) {
            auctionEvent.setAuctionType(AuctionType.COMMERCIAL);
        }
        else if (request.getCharityPercent() > 0) {
            auctionEvent.setAuctionType(AuctionType.CHARITY);
            auctionEvent.setCharityPercent(request.getCharityPercent());
        }

        auctionEvent.setImages(request.getImages());
        auctionEvent = auctionEventRepository.save(auctionEvent);

        auctionChatService.create(auctionEvent);
        notificationSenderService.sendNotificationOfCreatingAuction(auctionEvent);
        return auctionEventToDtoMapper.map(auctionEvent);
    }

    @Override
    @Transactional
    public void changeStatusToFinished(List<AuctionEvent> list) throws MessagingException, UnsupportedEncodingException {
        List<AuctionWinner> listOfWinners = new ArrayList<>();

        for (AuctionEvent event : list) {
            Optional<AuctionAction> auctionAction = auctionActionRepository.findTopByAuctionEventOrderByBetDesc(event);
            if (auctionAction.isEmpty()) {
                break;
                //0 bet
            }
            AuctionAction action = auctionAction.get();
            AuctionWinner auctionWinner = AuctionWinner.builder()
                    .auctionEvent(event)
                    .user(action.getUser())
                    .price(action.getBet())
                    .build();

            listOfWinners.add(auctionWinner);
            log.info("User[" + action.getUser() + "] won auctionEvent[" + event.getId() + "]");

            event.setStatusType(AuctionStatus.FINISHED);
            log.info("AuctionEvent [" + event.getId() + "] set new status - FINISHED.");

            paymentService.createPaymentForAuction(auctionWinner);

            log.info("Start to send email to winner " + auctionWinner.getUser().getEmail());
            mailService.sendEmailToAuctionWinner(auctionWinner);
            log.info("Finish to send email to winner");

            log.info("Start to send email to participants " + event.getTitle());
//            notificationSenderService.sendNotificationOfCreatingAuction();
            sendEmailToParticipants(event, auctionWinner);
            log.info("Finish to send email to participants");
        }

        auctionWinnerRepository.saveAll(listOfWinners);
        auctionEventRepository.saveAll(list);
    }

    @Override
    @Transactional(readOnly = true)
    public void sendEmailToParticipants(AuctionEvent auctionEvent, AuctionWinner auctionWinner) throws MessagingException, UnsupportedEncodingException {
        List<AuctionAction> list = auctionActionRepository.getAllByAuctionGroupByUser(auctionEvent.getId(), auctionWinner.getUser().getId());
        if (!list.isEmpty()) {
            mailService.sendEmailToAuctionParticipant(list);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public AuctionEventDto blockAuctionEventById(Long auctionId) {
        AuctionEvent auctionEvent = findById(auctionId);
        return auctionEventToDtoMapper.map(blockAuctionEvent(auctionEvent));
    }

    @Override
    @Transactional
    public AuctionEvent blockAuctionEvent(AuctionEvent auctionEvent) {
        auctionEvent.setStatusType(AuctionStatus.BLOCKED);
        log.info("AuctionEvent[" + auctionEvent.getId() + "] new status - " + AuctionStatus.BLOCKED.name());
        return auctionEventRepository.save(auctionEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuctionEventDto> search(String message, int page, int perPage) {
        Pageable pageable = PageRequest.of(page - 1, perPage);
        AuctionEventSpecification specification = new AuctionEventSpecification(new SearchCriteria("title", ":", message, true));

//        return auctionEventRepository.search(message, pageable, specification).map(auctionEventToDtoMapper::map);
        return auctionEventRepository.findAll(specification, pageable).map(auctionEventToDtoMapper::map);
    }

    @Override
    @Transactional
    public void changeStatusToStart(List<AuctionEvent> list) {
        list.forEach(e -> e.setStatusType(AuctionStatus.ACTIVE));
        auctionEventRepository.saveAll(list);
    }

    @Transactional
    public AuctionEvent finish(AuctionEvent auctionEvent) {
        auctionEvent.setStatusType(AuctionStatus.FINISHED);
        return auctionEventRepository.save(auctionEvent);
    }

    @Override
    @Transactional
    public void finishByFinishPrice(AuctionEvent auctionEvent, User user) {
        auctionEvent = finish(auctionEvent);
        auctionWinnerService.createForFinishPrice(auctionEvent, user);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuctionEventDto> getAllSortByRating(int page, int perPage) {
        Pageable pageable = PageRequest.of(page - 1, perPage);

        return auctionEventRepository.getAuctionEventByRating(pageable).map(auctionEventToDtoMapper::map);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuctionEventDto> get(int page,
                                     int perPage,
                                     List<Long> subCategoryIds) {
        Pageable pageable = PageRequest.of(page - 1, perPage);

        Page<AuctionEventDto> auctionEventDtos = null;
        if (subCategoryIds.size() == 1231231) {
//            auctionEventDtos = auctionEventRepository
//                    .findAllAndFilter(subCategoryIds, pageable)
//                    .map(auctionEventToDtoMapper::map);
        }
        else {
            auctionEventDtos = auctionEventRepository
                    .findAll(pageable)
                    .map(auctionEventToDtoMapper::map);
        }
        return auctionEventDtos;
    }

    @Override
    @Transactional
    public void delete(AuctionEvent auctionEvent) {
        if (auctionEvent.getStatusType().equals(AuctionStatus.FINISHED)) {
            AuctionWinner auctionWinner = auctionWinnerRepository.findByAuctionEvent(auctionEvent)
                    .orElseThrow(() -> new AuctionEventNotFoundException("Winner for auction[" + auctionEvent.getId() + "] not found!"));
            auctionWinnerRepository.delete(auctionWinner);
        }

        auctionChatService.deleteByAuction(auctionEvent);
        auctionEventSortRepository.deleteAllByAuctionEvent(auctionEvent);
        auctionActionRepository.deleteAllByAuctionEvent(auctionEvent);
        auctionEventRepository.delete(auctionEvent);
    }

    @Override
    @Transactional
    public void deleteById(Long auctionId) {
        AuctionEvent auctionEvent = findById(auctionId);
        delete(auctionEvent);
    }

    @Override
    @Transactional
    public AuctionEventDto update(AuctionEventRequest request, Long auctionId) {
        AuctionEvent auctionEvent = findById(auctionId);

        AuctionType oldAuctionType = auctionEvent.getAuctionType();

        auctionEvent.setId(auctionId);
        auctionEvent.setTitle(request.getTitle());
        auctionEvent.setDescription(request.getDescription());

        if (request.getAuctionStatus().equals(AuctionStatus.ACTIVE.name())) {
            auctionEvent.setStatusType(AuctionStatus.ACTIVE);
        }
        else if(request.getAuctionStatus().equals(AuctionStatus.EXPECTATION.name())) {
            auctionEvent.setStatusType(AuctionStatus.EXPECTATION);
        }

        if (oldAuctionType.name().equals(AuctionType.CHARITY.name()) &&
                !request.getAuctionType().equals(AuctionType.CHARITY.name())) {
            auctionEvent.setAuctionType(AuctionType.COMMERCIAL);
        }
        else if (request.getCharityPercent() > 0 &&
                auctionEvent.getAuctionType().equals(AuctionType.CHARITY)){
            auctionEvent.setCharityPercent(request.getCharityPercent());
        }
        else if (request.getCharityPercent() > 0 &&
                auctionEvent.getAuctionType().equals(AuctionType.COMMERCIAL)){
            auctionEvent.setCharityPercent(request.getCharityPercent());
        }
        return auctionEventToDtoMapper.map(auctionEvent);
    }

    @Override
    @Transactional(readOnly = true)
    public AuctionEvent findById(Long id) {
        return auctionEventRepository.findById(id)
                .orElseThrow(() -> new AuctionEventNotFoundException("Auction event[" + id + "] doesn't exist."));
    }

    @Override
    @Transactional(readOnly = true)
    public AuctionEventDto getById(Long auctionId) {
        return auctionEventToDtoMapper.map(findById(auctionId));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AuctionEventDto> findAuctionsByCategory(Long categoryId,
                                                        int page,
                                                        int perPage) {
        Pageable pageable = PageRequest.of(page - 1, perPage);

        return auctionEventRepository.findByCategory(categoryId, pageable).map(auctionEventToDtoMapper::map);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuctionEventDto> findAll() {
        return auctionEventToDtoMapper.mapList(auctionEventRepository.findAll());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuctionEvent> getListForFinish(AuctionStatus status) {
        return auctionEventRepository.getListForFinish(status.name());
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuctionEvent> getListForStart(AuctionStatus status) {
        return auctionEventRepository.getListForStart(status.name());
    }
}
