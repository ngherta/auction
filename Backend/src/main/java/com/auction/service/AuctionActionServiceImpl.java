package com.auction.service;

import com.auction.event.notification.ChangeBetEvent;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.WrongBetException;
import com.auction.model.AuctionAction;
import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.model.enums.AuctionStatus;
import com.auction.model.mapper.Mapper;
import com.auction.projection.LastBidProjection;
import com.auction.repository.AuctionActionRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.service.interfaces.AuctionActionService;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.response.LastBidResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
class AuctionActionServiceImpl implements AuctionActionService {

  private final AuctionActionRepository auctionActionRepository;
  private final AuctionEventRepository auctionEventRepository;
  private final AuctionEventService auctionEventService;
  private final Mapper<AuctionAction, AuctionActionDto> auctionActionToDtoMapper;
  private final Mapper<AuctionEvent, AuctionEventDto> auctionEventDtoMapper;
  private final UserService userService;
  private final ApplicationEventPublisher publisher;


  @Override
  @Transactional
  public AuctionActionDto bet(Double bet, Long auctionId, Long userId) {
    AuctionEvent auctionEvent = auctionEventService.findById(auctionId);

    Optional<AuctionAction> action = auctionActionRepository.findTopByAuctionEventOrderByBetDesc(auctionEvent);
    checkBet(auctionEvent, bet, action);

    User user = userService.findById(userId);

    return create(bet, auctionEvent, user);
  }

  private AuctionActionDto create(Double bet, AuctionEvent auctionEvent, User user) {
    if (auctionEvent.getFinishPrice() != null && auctionEvent.getFinishPrice() <= bet) {
      auctionEventService.finishByFinishPrice(auctionEvent, user);
      bet = auctionEvent.getFinishPrice();
    }

    AuctionAction auctionAction = AuctionAction
            .builder()
            .auctionEvent(auctionEvent)
            .user(user)
            .bet(bet)
            .genDate(LocalDateTime.now())
            .build();
    auctionActionRepository.save(auctionAction);

    publisher.publishEvent(new ChangeBetEvent(auctionAction));
    return auctionActionToDtoMapper.map(auctionAction);
  }

  @Override
  @Transactional(readOnly = true)
  public void checkBet(AuctionEvent auctionEvent, Double bet) {
    checkBetByStatus(auctionEvent);

    Optional<AuctionAction> action = auctionActionRepository.findTopByAuctionEventOrderByBetDesc(auctionEvent);
    checkBetDifference(bet, auctionEvent, action);
  }

  @Override
  @Transactional(readOnly = true)
  public void checkBet(AuctionEvent auctionEvent,
                       Double bet,
                       Optional<AuctionAction> auctionAction) {
    checkBetByStatus(auctionEvent);
    checkBetDifference(bet, auctionEvent, auctionAction);
  }

  public void checkBetDifference(Double bet,
                                 AuctionEvent auctionEvent,
                                 Optional<AuctionAction> action) {
    if (auctionEvent.getStartPrice() > bet) {
      throw new WrongBetException("Bet should be higher than Start Price!");
    }
    if (!action.isEmpty()) {
      double betDifference = (bet * 100 / action.get().getBet()) - 100;
      if (betDifference <= 5.0) {
        throw new WrongBetException("Bet should be 5 percent higher!");
      }
    }
  }

  private void checkBetByStatus(AuctionEvent auctionEvent) {
    if (auctionEvent.getStatusType() != AuctionStatus.ACTIVE) {
      throw new WrongBetException("Auction[" + auctionEvent.getId() + "] has status " + auctionEvent.getStatusType());
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<AuctionActionDto> getAllByAuctionId(Long auctionId) {
    AuctionEvent auctionEvent = auctionEventRepository.findById(auctionId)
            .orElseThrow(() -> new AuctionEventNotFoundException("AuctionEvent[" + auctionId + "] doesn't exist."));

    List<AuctionAction> list = auctionActionRepository.findByAuctionEvent(auctionEvent);
    return auctionActionToDtoMapper.mapList(list);
  }

  @Override
  @Transactional(readOnly = true)
  public List<LastBidResponse> getLastBidForAuction(List<Long> listOfAuctionsIds) {
    List<LastBidProjection> listOfProjections = getLasBidsFromId(listOfAuctionsIds);
    List<LastBidResponse> response = new ArrayList<>();
    listOfProjections.forEach(e -> response.add(LastBidResponse.builder()
                                                        .auctionId(e.getAuctionId())
                                                        .bid(getLastBidFromListById(listOfProjections, e.getAuctionId()))
                                                        .build()));
    return response;
  }

  @Transactional(readOnly = true)
  @Override
  public List<LastBidProjection> getLasBidsFromId(List<Long> auctionIds) {
    return auctionActionRepository.getLastBidByAuctionIds(auctionIds);
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AuctionEventDto> getAuctionsByParticipant(Long userId, int page, int perPage) {
    Pageable pageable = PageRequest.of(page - 1, perPage);
    return auctionActionRepository.findAuctionActionByParticipantAndGroupByAuction(userId, pageable)
            .map(e -> auctionEventDtoMapper.map(e.getAuctionEvent()));
  }

  @Override
  @Transactional
  public void bet(String terminal) {
    AuctionAction action = AuctionAction
        .builder()
        .bet(3D)
        .build();
  }

  @Override
  @Transactional
  public AuctionActionDto defaultBet(AuctionEvent currentAuctionEvent, User user) {

    Optional<AuctionAction> action = auctionActionRepository.findTopByAuctionEventOrderByBetDesc(currentAuctionEvent);
    Double defaultBet = currentAuctionEvent.getStartPrice();

    if (action.isPresent()) {
      defaultBet = action.get().getBet() * 1.05;
    }
    checkBet(currentAuctionEvent, defaultBet, action);


    return create(defaultBet, currentAuctionEvent, user);
  }

  private Double getLastBidFromListById(List<LastBidProjection> list, Long auctionId) {
    Double maxBid = 0D;
    for (LastBidProjection bid : list) {
      if (bid.getAuctionId().longValue() == auctionId) {
        maxBid = bid.getLastBid();
        break;
      }
    }
    return maxBid;
  }

}
