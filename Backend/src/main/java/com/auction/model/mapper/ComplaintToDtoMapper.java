package com.auction.model.mapper;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionEventComplaint;
import com.auction.model.User;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.ComplaintDto;
import com.auction.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ComplaintToDtoMapper implements Mapper<AuctionEventComplaint, ComplaintDto>{

  private final Mapper<AuctionEvent, AuctionEventDto> auctionEventToDtoMapper;
  private final Mapper<User, UserDto> userToDtoMapper;

  @Override
  public ComplaintDto map(AuctionEventComplaint entity) {
    return ComplaintDto.builder()
            .id(entity.getId())
            .user(userToDtoMapper.map(entity.getUser()))
            .auctionEvent(auctionEventToDtoMapper.map(entity.getAuctionEvent()))
            .genDate(entity.getGenDate())
            .message(entity.getMessage())
            .status(entity.getStatus())
            .build();
  }

}
