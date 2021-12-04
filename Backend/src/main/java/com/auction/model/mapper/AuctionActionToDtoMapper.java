package com.auction.model.mapper;

import com.auction.model.AuctionAction;
import com.auction.model.User;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class AuctionActionToDtoMapper implements Mapper<AuctionAction, AuctionActionDto>{

  private final Mapper<User, UserDto> userToDtoMapper;

  @Override
  public AuctionActionDto map(AuctionAction entity) {
    AuctionActionDto auctionActionDto = AuctionActionDto.builder()
            .id(entity.getId())
            .user(userToDtoMapper.map(entity.getUser()))
            .auctionEvent(entity.getAuctionEvent().getId())
            .bid(entity.getBet())
            .genDate(entity.getGenDate())
            .build();
    return auctionActionDto;
  }
}
