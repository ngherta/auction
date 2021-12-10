package com.auction.web.dto.request;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class BetRequest {
  @NotNull
  private Long auctionId;
  @NotNull
  private Long userId;
  @NotNull
  private Double bet;
}
