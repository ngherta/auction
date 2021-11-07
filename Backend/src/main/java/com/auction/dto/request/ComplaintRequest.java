package com.auction.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ComplaintRequest {

  private Long auctionEventId;
  private Long userId;
  private String message;

  @JsonCreator
  public ComplaintRequest(@JsonProperty Long userId,
                          @JsonProperty Long auctionEventId,
                          @JsonProperty String message) {
    this.userId = userId;
    this.auctionEventId = auctionEventId;
    this.message = message;
  }
}
