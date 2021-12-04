package com.auction.web.dto.request;

import lombok.Data;

@Data
public class BetRequest {

  private Long auctionId;
  private Long userId;
  private Double price;
}
