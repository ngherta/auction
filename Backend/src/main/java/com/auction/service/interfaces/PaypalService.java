package com.auction.service.interfaces;

import com.auction.model.PaymentOrder;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

public interface PaypalService {
  Payment createPayment(PaymentOrder paymentOrder) throws PayPalRESTException;

  Payment executePayment(String paymentId, String payerId) throws PayPalRESTException;

  String getLink(Payment payment);
}
