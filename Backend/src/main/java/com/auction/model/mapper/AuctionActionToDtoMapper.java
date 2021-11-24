package com.auction.model.mapper;

import com.auction.model.AuctionAction;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuctionActionToDtoMapper implements Mapper<AuctionAction, AuctionActionDto>{

  private final UserToDtoMapper userToDtoMapper;

  @Override
  public AuctionActionDto map(AuctionAction entity) {
    AuctionActionDto auctionActionDto = AuctionActionDto.builder()
            .id(entity.getId())
            .user(userToDtoMapper.map(entity.getUser()))
            .auctionEvent(entity.getAuctionEvent().getId())
            .bid(entity.getBet())
            .date(entity.getDate())
            .build();
    return auctionActionDto;
  }
}
