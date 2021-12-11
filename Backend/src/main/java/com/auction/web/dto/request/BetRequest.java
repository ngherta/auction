package com.auction.web.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class BetRequest {
  @NotNull
  private Long auctionId;
  @NotNull
  private Long userId;
  @NotNull
  private Double bet;
}
