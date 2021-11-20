package com.auction.web.dto;

import com.auction.model.AuctionAction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuctionActionDto {
  private Long id;
  private Long auctionEvent;
  private UserDto user;
  private Double bid;
  private Date date;
}
