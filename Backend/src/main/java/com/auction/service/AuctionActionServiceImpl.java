package com.auction.service;

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


  @Override
  @Transactional
  public AuctionActionDto bet(Double bet, Long auctionId, Long userId) {
    AuctionEvent auctionEvent = auctionEventService.findById(auctionId);

    checkBet(auctionEvent, bet);

    User user = userService.findById(userId);

    if (auctionEvent.getFinishPrice() != null && auctionEvent.getFinishPrice() <= bet) {
      auctionEventService.finishByFinishPrice(auctionEvent, user);
      bet = auctionEvent.getFinishPrice();
    }

    AuctionAction auctionAction = AuctionAction.builder()
            .auctionEvent(auctionEvent)
            .user(user)
            .bet(bet)
            .genDate(LocalDateTime.now())
            .build();

    return auctionActionToDtoMapper.map(auctionActionRepository.save(auctionAction));
  }

  @Override
  @Transactional(readOnly = true)
  public void checkBet(AuctionEvent auctionEvent, Double bet) {
    checkBetByStatus(auctionEvent);

    Optional<AuctionAction> action = auctionActionRepository.findTopByAuctionEventOrderByBetDesc(auctionEvent);
    checkBetDifference(bet, auctionEvent, action);
  }
//  @Override
  public void checkBetDifference(Double bet,
                                  AuctionEvent auctionEvent,
                                  Optional<AuctionAction> action) {
    if (action.isEmpty()) {
      if (auctionEvent.getStartPrice() > bet) {
        throw new WrongBetException("Bet should be higher than Start Price!");
      }
    }
    double betDifference = (bet * 100 / action.get().getBet()) - 100;
    if (betDifference < 5.0) {
      throw new WrongBetException("Bet should be 5 percent higher!");
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
