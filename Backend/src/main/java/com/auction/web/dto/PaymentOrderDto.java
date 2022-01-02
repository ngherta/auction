package com.auction.web.dto;

import com.auction.model.enums.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrderDto {
  private Double price;
  private String currency;
  private String link;
  private String paymentId;
  private AuctionEventDto auctionEvent;
  private PaymentStatus status;
}
