package com.auction.web.dto;

import com.auction.model.enums.AuctionWinnerStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuctionWinnerDto {
  private AuctionEventDto auctionEvent;
  private UserDto user;
  private Double price;
  private PaymentOrderDto paymentOrder;
  private String genDate;
  private AuctionWinnerStatus status;
}
