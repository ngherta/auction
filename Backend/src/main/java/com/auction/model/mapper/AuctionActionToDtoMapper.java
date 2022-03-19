package com.auction.model.mapper;

import com.auction.model.AuctionAction;
import com.auction.model.User;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
class AuctionActionToDtoMapper implements Mapper<AuctionAction, AuctionActionDto> {

  private final Mapper<User, UserDto> userToDtoMapper;

  @Override
  public AuctionActionDto map(AuctionAction entity) {
    return AuctionActionDto.builder()
        .id(entity.getId())
        .user(userToDtoMapper.map(entity.getUser()))
        .auctionEvent(entity.getAuctionEvent().getId())
        .status(entity.getAuctionEvent().getStatusType())
        .bid(new DecimalFormat("#0.00").format(entity.getBet()))
        .genDate(entity.getGenDate().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")))
        .build();
  }
}
