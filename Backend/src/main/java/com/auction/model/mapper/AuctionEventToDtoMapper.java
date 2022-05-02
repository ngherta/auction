package com.auction.model.mapper;

import com.auction.model.AuctionEvent;
import com.auction.model.SubCategory;
import com.auction.model.User;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
class AuctionEventToDtoMapper implements Mapper<AuctionEvent, AuctionEventDto> {

  private final Mapper<User, UserDto> userToDtoMapper;

  private static final String DATE_PATTERN = "dd-MM-yyyy HH:mm";

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
        .startDate(entity.getStartDate().format(DateTimeFormatter.ofPattern(DATE_PATTERN)))
        .finishDate(entity.getFinishDate().format(DateTimeFormatter.ofPattern(DATE_PATTERN)))
        .genDate(entity.getGenDate().format(DateTimeFormatter.ofPattern(DATE_PATTERN)))
        .charityPercent(entity.getCharityPercent())
        .images(entity.getImages())
        .categories(entity.getCategories()
                        .stream()
                        .map(SubCategory::getSubCategoryName)
                        .collect(Collectors.toList()))
        .build();
  }
}
