package com.auction.web.contoller;

import com.auction.service.interfaces.PaymentService;
import com.auction.web.dto.PaymentOrderDto;
import com.auction.web.dto.PaymentOrderWithAuctionEventDto;
import com.auction.web.dto.ReceivePayment;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:8081")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/payment")
public class PaymentController {

  private final PaymentService paymentService;

  @PostMapping("/success")
  public ResponseEntity<Void> successPay(@RequestParam("paymentId") String paymentId,
                                         @RequestParam("PayerID") String payerId,
                                         @RequestParam("token") String token) {
    paymentService.execute(paymentId, payerId);
    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<PaymentOrderDto> getPaymentOrder(@RequestParam Long auctionId) {
    return ResponseEntity.ok(paymentService.findByAuctionEvent(auctionId));
  }

  @GetMapping("/send/{userId}")
  public ResponseEntity<Page<PaymentOrderWithAuctionEventDto>> getPaymentOrderByUser(@PathVariable Long userId,
                                                                                     @RequestParam(defaultValue = "1") int page,
                                                                                     @RequestParam(defaultValue = "10") int perPage) {
    return ResponseEntity.ok(paymentService.findByUser(userId, page, perPage));
  }

  @GetMapping("/receive/{userId}")
  public ResponseEntity<Page<ReceivePayment>> getReceiveOrderByUser(@PathVariable Long userId,
                                                                    @RequestParam(defaultValue = "1") int page,
                                                                    @RequestParam(defaultValue = "10") int perPage) {
    return ResponseEntity.ok(paymentService.findReceivePaymentsByUser(userId, page, perPage));
  }
}
