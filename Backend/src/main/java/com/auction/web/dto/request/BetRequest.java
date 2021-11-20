package com.auction.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class BetRequest {

  private Long auctionId;
  private Long userId;
  private Double price;

  @JsonCreator
  public BetRequest(@JsonProperty Long auctionId,
                    @JsonProperty Long userId,
                    @JsonProperty Double price) {
    this.userId = userId;
    this.auctionId = auctionId;
    this.price = price;
  }
}
