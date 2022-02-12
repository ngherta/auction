package com.auction.service;

import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.PaymentNotFound;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.PaymentOrder;
import com.auction.model.enums.PaymentStatus;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.PaymentOrderRepository;
import com.auction.service.interfaces.PaymentService;
import com.auction.service.interfaces.PaypalService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.PaymentOrderDto;
import com.auction.web.dto.PaymentOrderWithAuctionEventDto;
import com.paypal.api.payments.Payment;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
class PaymentServiceImpl implements PaymentService {

  private final PaymentOrderRepository paymentOrderRepository;
  private final PaypalService paypalService;
  private final AuctionEventRepository auctionEventRepository;
  private final Mapper<PaymentOrder, PaymentOrderDto> paymentOrderDtoMapper;
  private final Mapper<PaymentOrder, PaymentOrderWithAuctionEventDto> paymentOrderWithAuctionEventDtoMapper;
  private final UserService userService;

  @Override
  @Transactional
  @SneakyThrows
  public void createPaymentForAuction(AuctionWinner auctionWinner) {
    PaymentOrder paymentOrder = PaymentOrder.builder()
            .auctionEvent(auctionWinner.getAuctionEvent())
            .description("Description of payment")
            .price(auctionWinner.getPrice())
            .currency("USD")
            .method("paypal")
            .user(auctionWinner.getUser())
            .intent("sale")
            .build();

    Payment payment = paypalService.createPayment(paymentOrder);
    paymentOrder.setPaymentId(payment.getId());

    String link = paypalService.getLink(payment);

    if (link != null) {
      paymentOrder.setLink(link);
      paymentOrder.setStatus(PaymentStatus.CREATED);
    }

    paymentOrderRepository.save(paymentOrder);
  }

  @Transactional(readOnly = true)
  @Override
  public PaymentOrderDto findByAuctionEvent(Long auctionId) {
    AuctionEvent auctionEvent = auctionEventRepository.findById(auctionId)
            .orElseThrow(() -> new AuctionEventNotFoundException("AuctionEvent[" + auctionId + "] not found!"));
    return findByAuctionEvent(auctionEvent);
  }

  @Transactional(readOnly = true)
  @Override
  public PaymentOrderDto findByAuctionEvent(AuctionEvent auctionEvent) {
    PaymentOrder paymentOrder = paymentOrderRepository.findByAuctionEvent(auctionEvent)
            .orElseThrow(() -> new PaymentNotFound("Payment for auctionEvent[" + auctionEvent.getId() + "] not found!"));
    return paymentOrderDtoMapper.map(paymentOrder);
  }

  @Override
  @Transactional
  @SneakyThrows
  public void execute(String paymentId, String payerId) {
    Payment payment = paypalService.executePayment(paymentId, payerId);

    PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentId(paymentId)
            .orElseThrow(() -> new PaymentNotFound("Payment order[" + paymentId + "] doesn't exist!"));

    if (payment.getState().equals("completed") || payment.getState().equals("approved")) {
      paymentOrder.setStatus(PaymentStatus.COMPLETED);
      paymentOrderRepository.save(paymentOrder);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public Page<PaymentOrderWithAuctionEventDto> findByUser(final Long userId,
                                                          final int page,
                                                          final int perPage) {
    Pageable pageable = PageRequest.of(page - 1, perPage);

    return paymentOrderRepository
            .findByUser(userService.findById(userId), pageable)
            .map(paymentOrderWithAuctionEventDtoMapper::map);
  }
}
