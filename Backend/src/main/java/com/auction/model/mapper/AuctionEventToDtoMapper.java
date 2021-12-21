package com.auction.model.mapper;

import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
class AuctionEventToDtoMapper implements Mapper<AuctionEvent, AuctionEventDto>{

  private final Mapper<User, UserDto> userToDtoMapper;

  @Override
  public AuctionEventDto map(AuctionEvent entity) {

    return AuctionEventDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .description(entity.getDescription())
            .statusType(entity.getStatusType())
            .auctionType(entity.getAuctionType())
            .startPrice(entity.getStartPrice())
            .finishPrice(entity.getFinishPrice())
            .user(userToDtoMapper.map(entity.getUser()))
            .startDate(entity.getStartDate().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")))
            .finishDate(entity.getFinishDate().format(DateTimeFormatter.ofPattern("dd-MM-yy hh:mm")))
            .genDate(entity.getGenDate().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")))
            .charityPercent(entity.getCharityPercent())
            .images(entity.getImages())
            .build();
  }
}
