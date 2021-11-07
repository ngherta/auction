package com.auction.dto.request;

import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.model.enums.ComplaintStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Date;

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
