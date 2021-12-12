package com.auction.web.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class AuctionFinishByFinishPriceRequest {
  @NotNull
  private Long auctionId;
  @NotNull
  private Long userId;
}