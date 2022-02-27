package com.auction.service;

import com.auction.event.notification.AuctionFinishingNotificationEvent;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.PaymentOrder;
import com.auction.model.User;
import com.auction.model.enums.AuctionWinnerStatus;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionWinnerRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.AuctionWinnerService;
import com.auction.service.interfaces.PaymentService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.AuctionWinnerDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionWinnerServiceImpl implements AuctionWinnerService {
  private final AuctionWinnerRepository auctionWinnerRepository;
  private final UserService userService;
//  private final AuctionEventService auctionEventService;
  private final Mapper<AuctionWinner, AuctionWinnerDto> auctionWinnerDtoMapper;
  private final PaymentService paymentService;
  private final ApplicationEventPublisher publisher;

  @Override
  @Transactional(readOnly = true)
  public Page<AuctionWinnerDto> getAllAuctionWinnerForUser(Long userId, int page, int perPage) {
    User user = userService.findById(userId);
    return getAllAuctionWinnerForUser(user, page, perPage);
  }

  @Override
  @Transactional
  @Async
  public void createForFinishPrice(AuctionEvent auctionEvent, User user) {
    create(auctionEvent, user, auctionEvent.getFinishPrice());
  }

  @Override
  @Transactional
  public AuctionWinner create(AuctionEvent event, User user, Double bet) {
    AuctionWinner auctionWinner = AuctionWinner.builder()
            .auctionEvent(event)
            .user(user)
            .price(bet)
            .status(AuctionWinnerStatus.CREATED)
            .genDate(LocalDateTime.now())
            .build();

    auctionWinner.setPaymentOrder(paymentService.createPaymentForAuction(auctionWinner));
    publisher.publishEvent(new AuctionFinishingNotificationEvent(auctionWinner));

    return auctionWinner;
  }

  @Override
  @Transactional
  public void paid(PaymentOrder paymentOrder) {
    AuctionWinner winner = auctionWinnerRepository.findByAuctionEvent(paymentOrder.getAuctionEvent())
            .orElseThrow(() -> new AuctionEventNotFoundException("Auction winner for auction [" + paymentOrder.getAuctionEvent().getId() + "] not found!"));
    winner.setStatus(AuctionWinnerStatus.PAID);
  }

  @Transactional
  @Override
  public void unPaid(AuctionWinner auctionWinner) {
    auctionWinner.setStatus(AuctionWinnerStatus.UNPAID);
//    auctionEventService.resetAuction(auctionWinner.getAuctionEvent(), true);
    //TODO: event
  }


  @Override
  @Transactional(readOnly = true)
  public Page<AuctionWinnerDto> getAllAuctionWinnerForUser(User user, int page, int perPage) {
    Pageable pageable = PageRequest.of(page - 1, perPage);
    return auctionWinnerRepository.findByUser(user, pageable).map(auctionWinnerDtoMapper::map);
  }
}
