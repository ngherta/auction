package com.auction.web.dto;

import com.auction.model.enums.AuctionWinnerStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuctionWinnerDto {
  private AuctionEventDto auctionEvent;
  private UserDto user;
  private String price;
  private PaymentOrderDto paymentOrder;
  private String genDate;
  private AuctionWinnerStatus status;
  private String country;
  private String city;
  private String address;
  private Boolean hasDefaultAddress;
  private Boolean needAddress;
  private String trackNumber;
}
