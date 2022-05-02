package com.auction.service;

import com.auction.event.notification.AuctionCreationNotificationEvent;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.AuctionRuntimeException;
import com.auction.model.AuctionAction;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.SubCategory;
import com.auction.model.User;
import com.auction.model.enums.AuctionStatus;
import com.auction.model.enums.AuctionType;
import com.auction.model.mapper.Mapper;
import com.auction.projection.AuctionSearchProjection;
import com.auction.repository.AuctionActionRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.AuctionEventSortRepository;
import com.auction.repository.AuctionWinnerRepository;
import com.auction.service.interfaces.AuctionChatService;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.AuctionSpecificationFilter;
import com.auction.service.interfaces.AuctionWinnerService;
import com.auction.service.interfaces.CategoryService;
import com.auction.service.interfaces.MailService;
import com.auction.service.interfaces.PaymentService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.AuctionSearchDto;
import com.auction.web.dto.request.AuctionEventRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
  private final AuctionEventSortRepository auctionEventSortRepository;
  private final CategoryService categoryService;
  private final ApplicationEventPublisher publisher;
  private final UserService userService;
  private final MailService mailService;
  private final AuctionChatService auctionChatService;
  private final Mapper<AuctionEvent, AuctionEventDto> auctionEventToDtoMapper;
  private final PaymentService paymentService;
  private final AuctionSpecificationFilter auctionSpecificationFilter;

  private void checkDateForAuction(AuctionEventRequest request) {
    if (request.getFinishDate().isBefore(LocalDateTime.now())) {
      throw new DateTimeException("Finish date should be before " + LocalDateTime.now());
    }

    if (request.getStartDate().isAfter(request.getFinishDate())) {
      throw new DateTimeException("Start date should be before Finish date!");
    }
  }

  @Override
  @Transactional
  public AuctionEventDto save(AuctionEventRequest request) {
    checkDateForAuction(request);

    List<SubCategory> subCategoryList = categoryService.getSubCategoriesByIds(request.getCategoryIds());

    User user = userService.findById(request.getUserId());
    AuctionEvent auctionEvent = AuctionEvent.builder()
        .title(request.getTitle())
        .description(request.getDescription())
        .user(user)
        .startPrice(request.getStartPrice())
        .finishPrice(request.getFinishPrice())
        .statusType(AuctionStatus.EXPECTATION)
        .startDate(request.getStartDate().isAfter(LocalDateTime.now())
                       ? request.getStartDate()
                       : LocalDateTime.now())
        .finishDate(request.getFinishDate())
        .genDate(LocalDateTime.now())
        .categories(subCategoryList)
        .build();

    if (request.getAuctionType() == AuctionType.CHARITY &&
        request.getCharityPercent() != null &&
        request.getCharityPercent() == 0) {
      auctionEvent.setAuctionType(AuctionType.COMMERCIAL);
    }
    else if (request.getCharityPercent() > 0) {
      auctionEvent.setAuctionType(AuctionType.CHARITY);
      auctionEvent.setCharityPercent(request.getCharityPercent());
    }

    if (request.getStartDate().isBefore(LocalDateTime.now())) {
      auctionEvent.setStatusType(AuctionStatus.ACTIVE);
    }

    if(request.getFinishDate().isBefore(request.getStartDate())) {
      throw new AuctionRuntimeException("Finish date should be after start date");
    }

    auctionEvent.setImages(request.getImages());
    auctionEvent = auctionEventRepository.save(auctionEvent);

    auctionChatService.create(auctionEvent);

    publisher.publishEvent(new AuctionCreationNotificationEvent(auctionEvent));
    return auctionEventToDtoMapper.map(auctionEvent);
  }

  @Transactional
  @Override
  public void resetAuction(AuctionEvent auctionEvent, boolean actions) {
    auctionEvent.setStatusType(AuctionStatus.FOR_RESET);
    auctionEvent.setStartDate(null);
    auctionEvent.setFinishDate(null);

    if (actions) resetWithActions(auctionEvent);
    auctionChatService.deleteByAuction(auctionEvent);
  }

  private void resetWithActions(AuctionEvent auctionEvent) {
    auctionActionRepository.deleteAllByAuctionEvent(auctionEvent);
  }

  @Override
  @Transactional
  public void changeStatusToFinished(List<AuctionEvent> list) throws MessagingException, UnsupportedEncodingException {
    List<AuctionWinner> listOfWinners = new ArrayList<>();

    for (AuctionEvent event : list) {
      Optional<AuctionAction> auctionAction = auctionActionRepository.findTopByAuctionEventOrderByBetDesc(event);
      if (auctionAction.isEmpty()) {
        resetAuction(event, true);
        continue;
      }
      AuctionAction action = auctionAction.get();

      event.setStatusType(AuctionStatus.FINISHED);
      log.info("AuctionEvent [" + event.getId() + "] set new status - FINISHED.");

      AuctionWinner auctionWinner = auctionWinnerService.create(event, action.getUser(), action.getBet());

      listOfWinners.add(auctionWinner);
      log.info("User[" + action.getUser() + "] win auctionEvent[" + event.getId() + "]");
      //TODO:notification!
      mailService.sendEmailToAuctionWinner(auctionWinner);
      sendEmailToParticipants(event, auctionWinner);
    }

    auctionWinnerRepository.saveAll(listOfWinners);
    auctionEventRepository.saveAll(list);
  }

  @Override
  @Transactional(readOnly = true)
  public void sendEmailToParticipants(AuctionEvent auctionEvent, AuctionWinner auctionWinner) throws MessagingException, UnsupportedEncodingException {
    List<AuctionAction> list = auctionActionRepository.getAllByAuctionGroupByUser(auctionEvent.getId());
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
//    AuctionEventSpecification specification = new AuctionEventSpecification(new SearchCriteria("title", ":", message, true));

//        return auctionEventRepository.search(message, pageable, specification).map(auctionEventToDtoMapper::map);
//    return auctionEventRepository.findAll(specification, pageable).map(auctionEventToDtoMapper::map);
    return null;
  }

  @Override
  @Transactional
  public void changeStatusToStart(List<AuctionEvent> list) {
    list.forEach(e -> e.setStatusType(AuctionStatus.ACTIVE));
    auctionEventRepository.saveAll(list);
  }

  @Transactional
  @Override
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
                                   int perPage) {
    Pageable pageable = PageRequest.of(page - 1, perPage);

    return auctionEventRepository
        .findAll(pageable)
        .map(auctionEventToDtoMapper::map);
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

    if (oldAuctionType == AuctionType.CHARITY &&
        request.getAuctionType() != AuctionType.CHARITY) {
      auctionEvent.setAuctionType(AuctionType.COMMERCIAL);
    }
    else if (request.getCharityPercent() > 0 &&
        auctionEvent.getAuctionType() == AuctionType.CHARITY) {
      auctionEvent.setCharityPercent(request.getCharityPercent());
    }
    else if (request.getCharityPercent() > 0 &&
        auctionEvent.getAuctionType() == AuctionType.COMMERCIAL) {
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

  @Transactional(readOnly = true)
  @Override
  public Page<AuctionEventDto> findAllAndFilter(int page,
                                                int perPage,
                                                String title,
                                                List<Long> categoriesIds,
                                                List<AuctionStatus> statuses) {
    Pageable pageable = PageRequest.of(page - 1, perPage);
    Specification<AuctionEvent> specification = null;
    specification = auctionSpecificationFilter.createFilter(title, categoriesIds, statuses);

    return auctionEventRepository.findAll(specification, pageable).map(auctionEventToDtoMapper::map);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AuctionEventDto> getAllByOwner(Long userId,
                                             int page,
                                             int perPage) {
    User user = userService.findById(userId);
    Pageable pageable = PageRequest.of(page - 1, perPage);
    return auctionEventRepository.findByUser(user, pageable).map(auctionEventToDtoMapper::map);
  }

  @Override
  @Transactional(readOnly = true)
  public List<AuctionSearchDto> reactiveSearch(String message) {
    List<AuctionSearchProjection> listOfProjection = auctionEventRepository.findByText(message, 10);
    List<AuctionSearchDto> result = new ArrayList<>();
    for (AuctionSearchProjection e : listOfProjection) {
      result.add(AuctionSearchDto.builder()
                     .id(e.getId())
                     .title(e.getTitle())
                     .status(e.getStatus())
                     .startDate(e.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")))
                     .finishDate(e.getFinishDate().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")))
                     .build());
    }
    return result;
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AuctionEventDto> getAuctionsByParticipant(Long userId, int page, int perPage) {
    Pageable pageable = PageRequest.of(page - 1, perPage);
    return auctionEventRepository.findByUserParticipant(userId, pageable)
        .map(auctionEventToDtoMapper::map);
  }

  @Override
  @Transactional(readOnly = true)
  public List<AuctionEvent> getListForStart(AuctionStatus status) {
    return auctionEventRepository.getListForStart(status.name());
  }
}
