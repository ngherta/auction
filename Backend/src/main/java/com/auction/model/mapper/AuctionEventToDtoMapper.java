package com.auction.model.mapper;

import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
class AuctionEventToDtoMapper implements Mapper<AuctionEvent, AuctionEventDto>{

  private final Mapper<User, UserDto> userToDtoMapper;

  @Override
  public AuctionEventDto map(AuctionEvent entity) {
    AuctionEventDto auctionEventDto = AuctionEventDto.builder()
            .id(entity.getId())
            .title(entity.getTitle())
            .description(entity.getDescription())
            .statusType(entity.getStatusType())
            .auctionType(entity.getAuctionType())
            .startPrice(entity.getStartPrice())
            .finishPrice(entity.getFinishPrice())
            .user(userToDtoMapper.map(entity.getUser()))
            .startDate(entity.getStartDate())
            .finishDate(entity.getFinishDate())
            .genDate(entity.getGenDate())
            .charityPercent(entity.getCharityPercent())
            .build();
    List<String> images = new ArrayList<>();
    entity.getImages().forEach(e -> images.add(e.getUrl()));
    auctionEventDto.setImages(images);

    return auctionEventDto;
  }
}
