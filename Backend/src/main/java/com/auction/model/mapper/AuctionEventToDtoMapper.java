package com.auction.model.mapper;

import com.auction.model.AuctionEvent;
import com.auction.web.dto.AuctionEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AuctionEventToDtoMapper implements Mapper<AuctionEvent, AuctionEventDto>{

  private final UserToDtoMapper userToDtoMapper;

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
    entity.getImages().stream().map(e -> images.add(e.getUrl()));
    auctionEventDto.setImages(images);

    return auctionEventDto;
  }
}
