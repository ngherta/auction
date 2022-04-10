package com.auction.service;

import com.auction.model.PaymentOrder;
import com.auction.service.interfaces.PaypalService;
import com.paypal.api.payments.Links;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.paypal.api.payments.Amount;
import com.paypal.api.payments.Payer;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.PaymentExecution;
import com.paypal.api.payments.RedirectUrls;
import com.paypal.api.payments.Transaction;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
class PaypalServiceImpl implements PaypalService {

  public static final String SUCCESS_URL = "/payment/success";
  public static final String CANCEL_URL = "/auctions";

  @Value("${spring.client.url}")
  private String clientUrl;

  private final APIContext apiContext;

  @Override
  @Transactional
  public Payment createPayment(PaymentOrder paymentOrder) throws PayPalRESTException {
    Amount theAmount = new Amount();
    theAmount.setCurrency(paymentOrder.getCurrency());
    Double total = BigDecimal.valueOf(paymentOrder.getPrice()).setScale(2, RoundingMode.HALF_UP).doubleValue();
    theAmount.setTotal(String.format("%.2f", total));

    Transaction transaction = new Transaction();
    transaction.setDescription(paymentOrder.getDescription());
    transaction.setAmount(theAmount);

    List<Transaction> theTransactions = new ArrayList<>();
    theTransactions.add(transaction);

    Payer thePayer = new Payer();
    thePayer.setPaymentMethod(paymentOrder.getMethod());

    Payment thePayment = new Payment();
    thePayment.setIntent(paymentOrder.getIntent());
    thePayment.setTransactions(theTransactions);
    thePayment.setPayer(thePayer);

    RedirectUrls theRedirectUrls = new RedirectUrls();
    theRedirectUrls.setCancelUrl(clientUrl + CANCEL_URL);
    theRedirectUrls.setReturnUrl(clientUrl + SUCCESS_URL);
    thePayment.setRedirectUrls(theRedirectUrls);

    return thePayment.create(apiContext);
  }

  @Override
  @SneakyThrows
  @Transactional
  public Payment executePayment(String paymentId, String payerId) {

    Payment thePayment = new Payment();
    thePayment.setId(paymentId);

    PaymentExecution thPaymentExecution = new PaymentExecution();
    thPaymentExecution.setPayerId(payerId);

    return thePayment.execute(apiContext, thPaymentExecution);
  }

  @Override
  public String getLink(Payment payment) {
    for (Links links : payment.getLinks()) {
      if (links.getRel().equals("approval_url")) {
        return links.getHref();
      }
    }
    return null;
  }
}
