package com.auction.dto;

import com.auction.model.AuctionAction;
import com.auction.model.AuctionEvent;
import com.auction.model.User;
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
  private Date date;

  public static List<AuctionActionDto> from(List<AuctionAction> auctionActionList) {
    List<AuctionActionDto> auctionActionDtoList = new ArrayList<>();
    for (AuctionAction auctionAction : auctionActionList) {
      AuctionActionDto auctionActionDto = AuctionActionDto.builder()
              .id(auctionAction.getId())
              .user(UserDto.from(auctionAction.getUser()))
              .auctionEvent(auctionAction.getAuctionEvent().getId())
              .date(auctionAction.getDate())
              .build();
      auctionActionDtoList.add(auctionActionDto);
    }
    return auctionActionDtoList;
  }
}