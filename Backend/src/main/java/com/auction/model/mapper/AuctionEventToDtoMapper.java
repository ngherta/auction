package com.auction.model.mapper;

import com.auction.model.AuctionEvent;
import com.auction.web.dto.AuctionEventDto;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import java.util.ArrayList;
import java.util.List;

@Component
public class AuctionEventToDtoMapper implements Mapper<AuctionEvent, AuctionEventDto>{

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
            .user(entity.getUser())
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
