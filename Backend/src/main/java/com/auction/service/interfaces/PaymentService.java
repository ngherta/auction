package com.auction.service.interfaces;

import com.auction.model.User;
import com.auction.web.dto.request.CreatePaymentRequest;

public interface PaymentService {
  boolean checkPayment(User user);

  void create(CreatePaymentRequest request);
}
