package com.auction.web.dto;

import com.auction.model.enums.ComplaintStatus;
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
public class ComplaintDto {
  private Long id;
  private AuctionEventDto auctionEvent;
  private UserDto user;
  private String message;
  private ComplaintStatus status;
  private String genDate;
}
