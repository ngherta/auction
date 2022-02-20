package com.auction.service;

import com.auction.event.notification.AuctionFinishingNotificationEvent;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.User;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionWinnerRepository;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuctionWinnerServiceImpl implements AuctionWinnerService {
  private final AuctionWinnerRepository auctionWinnerRepository;
  private final UserService userService;
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
    AuctionWinner auctionWinner = AuctionWinner.builder()
            .user(user)
            .auctionEvent(auctionEvent)
            .price(auctionEvent.getFinishPrice())
            .build();

    auctionWinnerRepository.save(auctionWinner);
    paymentService.createPaymentForAuction(auctionWinner);

    publisher.publishEvent(new AuctionFinishingNotificationEvent(auctionWinner));
  }

  @Override
  @Transactional(readOnly = true)
  public Page<AuctionWinnerDto> getAllAuctionWinnerForUser(User user, int page, int perPage) {
    Pageable pageable = PageRequest.of(page - 1, perPage);
    return auctionWinnerRepository.findByUser(user, pageable).map(auctionWinnerDtoMapper::map);
  }
}
