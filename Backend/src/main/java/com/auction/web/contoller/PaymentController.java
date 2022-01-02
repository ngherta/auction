package com.auction.web.contoller;

import com.auction.service.interfaces.PaymentService;
import com.auction.service.interfaces.PaypalService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin("http://localhost:8081")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

  private final PaymentService paymentService;

  @GetMapping("/success")
  public String successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId) {

    paymentService.execute(paymentId, payerId);
    return "redirect:/";
  }

}
