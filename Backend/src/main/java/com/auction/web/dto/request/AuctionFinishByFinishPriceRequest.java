package com.auction.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuctionFinishByFinishPriceRequest {

  private Long auctionId;
  private Long userId;
}