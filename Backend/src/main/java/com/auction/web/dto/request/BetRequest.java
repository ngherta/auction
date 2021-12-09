package com.auction.web.dto.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class BetRequest {
  private Long auctionId;
  private Long userId;
  private Double bet;
}
