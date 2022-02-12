package com.auction.web.dto;

import com.auction.model.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentOrderWithAuctionEventDto {
  private Long id;
  private AuctionEventDto auctionEvent;
  private Double price;
  private String currency;
  private String link;
  private String paymentId;
  private PaymentStatus status;
}
