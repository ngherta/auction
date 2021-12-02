package com.auction.service;

import com.auction.exception.UserDoesntHavePaymentException;
import com.auction.exception.UserNotFoundException;
import com.auction.model.Payment;
import com.auction.model.User;
import com.auction.repository.PaymentRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.PaymentService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.request.CreatePaymentRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

  private final PaymentRepository paymentRepository;
  private final UserRepository userRepository;
  private final UserService userService;

  @Override
  @Transactional(readOnly = true)
  public boolean checkPayment(User user) {
    List<Payment> list = paymentRepository.findAllByUser(user);
    if (list.isEmpty()) {
      throw new UserDoesntHavePaymentException("User[" + user.getId() + "] doesn't have payment method!");
    }
    return true;
  }

  @Override
  @Transactional
  public void create(CreatePaymentRequest request) {
    Payment payment = Payment.builder()
            .cardNumber(request.getCardNumber())
            .cvv(request.getCvv())
            .expirationDate(request.getExpirationDate())
            .name(request.getName())
            .build();

    User user = userService.findById(request.getUserId());

    payment.setUser(user);

    paymentRepository.save(payment);
  }
}
