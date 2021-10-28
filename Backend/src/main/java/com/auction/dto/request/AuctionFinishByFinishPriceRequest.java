package com.auction.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuctionFinishByFinishPriceRequest {

  private Long auctionId;
  private Long userId;

  @JsonCreator
  public AuctionFinishByFinishPriceRequest(@JsonProperty Long auctionId,
                             @JsonProperty Long userId) {
    this.userId = userId;
    this.auctionId = auctionId;
  }
}