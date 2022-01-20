package com.auction.service;

import com.auction.exception.PaymentNotFound;
import com.auction.model.AuctionWinner;
import com.auction.model.PaymentOrder;
import com.auction.model.enums.PaymentStatus;
import com.auction.model.mapper.Mapper;
import com.auction.repository.PaymentOrderRepository;
import com.auction.service.interfaces.PaymentService;
import com.auction.service.interfaces.PaypalService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.PaymentOrderDto;
import com.paypal.api.payments.Payment;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
class PaymentServiceImpl implements PaymentService {

  private final PaymentOrderRepository paymentOrderRepository;
  private final PaypalService paypalService;
  private final UserService userService;
  private final Mapper<PaymentOrder, PaymentOrderDto> paymentOrderDtoMapper;

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
}
