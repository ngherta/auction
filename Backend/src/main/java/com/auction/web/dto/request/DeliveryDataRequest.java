package com.auction.web.dto.request;

import lombok.Data;

@Data
public class DeliveryDataRequest {
  private String trackNumber;
  private Long auctionId;
}
