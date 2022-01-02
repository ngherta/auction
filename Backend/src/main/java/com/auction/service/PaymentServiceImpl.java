package com.auction.service;

import com.auction.exception.PaymentNotFound;
import com.auction.model.AuctionWinner;
import com.auction.model.PaymentOrder;
import com.auction.model.enums.PaymentStatus;
import com.auction.repository.PaymentOrderRepository;
import com.auction.service.interfaces.PaymentService;
import com.auction.service.interfaces.PaypalService;
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
    System.out.println(payment.toJSON());

    PaymentOrder paymentOrder = paymentOrderRepository.findByPaymentId(paymentId)
            .orElseThrow(() -> new PaymentNotFound("Payment order[" + paymentId + "] doesn't exist!"));

    if (payment.getState().equals("completed") || payment.getState().equals("approved")) {
      System.out.println("Payment executed!");
      paymentOrder.setStatus(PaymentStatus.COMPLETED);
      paymentOrderRepository.save(paymentOrder);
    }
  }
}
