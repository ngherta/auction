package com.auction.service;

import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.PaymentNotFound;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.PaymentAudit;
import com.auction.model.PaymentOrder;
import com.auction.model.User;
import com.auction.model.enums.PaymentStatus;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.PaymentAuditRepository;
import com.auction.repository.PaymentOrderRepository;
import com.auction.service.interfaces.PaymentService;
import com.auction.service.interfaces.PaypalService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.PaymentOrderDto;
import com.auction.web.dto.PaymentOrderWithAuctionEventDto;
import com.auction.web.dto.ReceivePayment;
import com.auction.web.dto.response.statistic.CommissionPerMouth;
import com.paypal.api.payments.Payment;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class PaymentServiceImpl implements PaymentService {

  private final PaymentOrderRepository paymentOrderRepository;
  private final PaypalService paypalService;
  private final AuctionEventRepository auctionEventRepository;
  private final Mapper<PaymentOrder, PaymentOrderDto> paymentOrderDtoMapper;
  private final Mapper<PaymentOrder, PaymentOrderWithAuctionEventDto> paymentOrderWithAuctionEventDtoMapper;
  private final Mapper<PaymentAudit, ReceivePayment> receivePaymentMapper;
  private final UserService userService;
  private final PaymentAuditRepository paymentAuditRepository;

  private static final Double COMMISSION = 5D;

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

    if (payment.getState().equals("completed") ||
            payment.getState().equals("approved")) {
      paymentOrder.setStatus(PaymentStatus.COMPLETED);
      paymentOrderRepository.save(paymentOrder);
      createAudit(paymentOrderRepository.save(paymentOrder));
    }
  }

  @Transactional
  @Override
  public void createAudit(PaymentOrder paymentOrder) {
    Double amount = takeCommission(paymentOrder);
    PaymentAudit paymentAudit = PaymentAudit.builder()
            .amount(amount)
            .currency(paymentOrder.getCurrency())
            .recipient(paymentOrder.getAuctionEvent().getUser())
            .sender(paymentOrder.getUser())
            .paymentOrder(paymentOrder)
            .genDate(LocalDateTime.now())
            .commission(false)
            .build();

    paymentAuditRepository.save(paymentAudit);
  }

  @Transactional
  @Override
  public Double takeCommission(PaymentOrder paymentOrder) {
    Double amount = paymentOrder.getPrice() * COMMISSION / 100;
    PaymentAudit paymentAudit = PaymentAudit
            .builder()
            .paymentOrder(paymentOrder)
            .sender(paymentOrder.getUser())
            .currency(paymentOrder.getCurrency())
            .amount(amount)
            .recipient(userService.findMainAdmin().get())
            .commission(true)
            .genDate(LocalDateTime.now())
            .build();
    paymentAuditRepository.save(paymentAudit);
    return paymentOrder.getPrice() - amount;
  }

  @Transactional(readOnly = true)
  @Override
  public Page<ReceivePayment> findReceivePaymentsByUser(final Long userId,
                                                                         final int page,
                                                                         final int perPage) {
    Pageable pageable = PageRequest.of(page - 1, perPage);
    return paymentAuditRepository
            .findAllByRecipient(userService.findById(userId), pageable)
            .map(receivePaymentMapper::map);
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
