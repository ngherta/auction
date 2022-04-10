package com.auction.web.dto;

import com.auction.model.enums.AuctionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuctionActionDto {
  private Long id;
  private Long auctionEvent;
  private AuctionStatus status;
  private UserDto user;
  private String bid;
  private String genDate;
}
