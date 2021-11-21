package com.auction.service;

import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.StartPriceNullException;
import com.auction.exception.UserNotFoundException;
import com.auction.model.AuctionAction;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.User;
import com.auction.model.enums.AuctionStatus;
import com.auction.model.enums.AuctionType;
import com.auction.model.mapper.AuctionEventToDtoMapper;
import com.auction.repository.AuctionActionRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.AuctionEventSortRepository;
import com.auction.repository.AuctionWinnerRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.MailService;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.request.AuctionEventRequest;
import com.auction.web.dto.request.AuctionFinishByFinishPriceRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuctionEventServiceImpl implements AuctionEventService {

    private final AuctionEventRepository auctionEventRepository;
    private final UserRepository userRepository;
    private final AuctionWinnerRepository auctionWinnerRepository;
    private final AuctionActionRepository auctionActionRepository;
    private final AuctionEventSortRepository auctionEventSortRepository;
    private final MailService mailService;
    private final AuctionEventToDtoMapper auctionEventToDtoMapper;

    @Override
    @Transactional
    public AuctionEventDto save(AuctionEventRequest request) {
        AuctionEvent auctionEvent = new AuctionEvent();
        auctionEvent.setTitle(request.getTitle());
        auctionEvent.setDescription(request.getDescription());

        User user = userRepository.findById(request.getUserId()).get();
        auctionEvent.setUser(user);

        if (request.getStartPrice() == null) {
            throw new StartPriceNullException("Start price is null");
        }

        auctionEvent.setStartPrice(request.getStartPrice());
        auctionEvent.setFinishPrice(request.getFinishPrice());
        auctionEvent.setStatusType(AuctionStatus.EXPECTATION);
        auctionEvent.setGenDate(new Date());
        auctionEvent.setStartDate(request.getStartDate());
        auctionEvent.setFinishDate(request.getFinishDate());

        if (request.getCharityPercent() == 0) {
            auctionEvent.setAuctionType(AuctionType.COMMERCIAL);
        }
        else if (request.getCharityPercent() > 0) {
            auctionEvent.setAuctionType(AuctionType.CHARITY);
            auctionEvent.setCharityPercent(request.getCharityPercent());
        }

        auctionEvent = auctionEventRepository.save(auctionEvent);

        return auctionEventToDtoMapper.map(auctionEvent);
    }

    @Override
    @Transactional
    public void changeStatusToFinished(List<AuctionEvent> list) throws MessagingException, UnsupportedEncodingException {
        List<AuctionWinner> listOfWinners = new ArrayList<>();

        for (AuctionEvent event : list) {
            AuctionWinner auctionWinner = new AuctionWinner();
            auctionWinner.setAuctionEvent(event);

            AuctionAction auctionAction = auctionActionRepository.getLastAuctionActionByAuctionEventOrderByBetDesc(event);
            auctionWinner.setUser(auctionAction.getUser());
            auctionWinner.setPrice(auctionAction.getBet());

            listOfWinners.add(auctionWinner);
            log.info("User[" + auctionAction.getUser() + "] won auctionEvent[" + event.getId() + "]");

            event.setStatusType(AuctionStatus.FINISHED);
            log.info("AuctionEvent [" + event.getId() + "] set new status - FINISHED.");

            log.info("Start to send email to winner " + auctionWinner.getUser().getEmail());
            mailService.sendEmailToAuctionWinner(auctionWinner);
            log.info("Finish to send email to winner");

            log.info("Start to send email to participants " + event.getTitle());
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
        AuctionEvent auctionEvent = auctionEventRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionEventNotFoundException("AuctionEvent[" + auctionId + "doesn't exist."));
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
    public void search(String message) {
        int limit = 5;
        auctionEventRepository.search(message, limit);
    }

    @Override
    @Transactional
    public void changeStatusToStart(List<AuctionEvent> list) {
        list.stream().forEach(e -> e.setStatusType(AuctionStatus.ACTIVE));
        auctionEventRepository.saveAll(list);
    }

    @Override
    @Transactional
    public void finishByFinishPrice(AuctionFinishByFinishPriceRequest request) {
        AuctionEvent auctionEvent = auctionEventRepository.findById(request.getAuctionId())
                .orElseThrow(() -> new AuctionEventNotFoundException("Auction event[" + request.getAuctionId() + "doesn't exist"));
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User[" + request.getUserId() + "] doesn't exist"));

        auctionEvent.setStatusType(AuctionStatus.FINISHED);
        auctionEventRepository.save(auctionEvent);


        AuctionWinner auctionWinner = new AuctionWinner();
        auctionWinner.setUser(user);
        auctionWinner.setAuctionEvent(auctionEvent);
        auctionWinner.setPrice(auctionEvent.getFinishPrice());
        auctionWinnerRepository.save(auctionWinner);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuctionEventDto> getAllSortByRating() {
        List<AuctionEvent> list = auctionEventRepository.getAuctionEventByRating();
        return auctionEventToDtoMapper.mapList(list);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuctionEventDto> getAll() {
        List<AuctionEvent> list = auctionEventRepository.findAll();
        return auctionEventToDtoMapper.mapList(list);
    }

    @Override
    @Transactional
    public void delete(AuctionEvent auctionEvent) {
        if (auctionEvent.getStatusType().equals(AuctionStatus.FINISHED)) {
            AuctionWinner auctionWinner = auctionWinnerRepository.findByAuctionEvent(auctionEvent.getId());
            auctionWinnerRepository.delete(auctionWinner);
        }

        auctionEventSortRepository.deleteAllByAuctionEvent(auctionEvent.getId());
        auctionActionRepository.deleteAllByAuctionEvent(auctionEvent);
        auctionEventRepository.delete(auctionEvent);
    }

    @Override
    public void deleteById(Long auctionId) {
        AuctionEvent auctionEvent = auctionEventRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionEventNotFoundException("Auction event["+ auctionId + "] doesn't exist."));
        delete(auctionEvent);
    }

    @Override
    @Transactional
    public AuctionEventDto update(AuctionEventRequest request, Long auctionId) {
        AuctionEvent auctionEvent = auctionEventRepository.findById(auctionId)
                .orElseThrow(() -> new AuctionEventNotFoundException("Auction event[" + auctionId + "] doesn't exist."));

        ///Need to add more exceptions!
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
                auctionEvent.getAuctionType().equals(AuctionType.CHARITY.name())){
            auctionEvent.setCharityPercent(request.getCharityPercent());
        }
        else if (request.getCharityPercent() > 0 &&
                auctionEvent.getAuctionType().equals(AuctionType.COMMERCIAL.name())){
            auctionEvent.setCharityPercent(request.getCharityPercent());
        }

        return auctionEventToDtoMapper.map(auctionEvent);
    }
}
