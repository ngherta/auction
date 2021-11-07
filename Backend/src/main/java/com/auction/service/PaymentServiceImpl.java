package com.auction.service;

import com.auction.web.model.Payment;
import com.auction.web.model.User;
import com.auction.repository.PaymentRepository;
import com.auction.service.interfaces.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;

  @Override
  public void checkPayment(User user) {
    List<Payment> list = paymentRepository.findAllByUser(user);
    if (list.isEmpty()) {
    }
  }
}
