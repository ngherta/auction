package com.auction.model.mapper;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.PaymentOrder;
import com.auction.model.User;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.AuctionWinnerDto;
import com.auction.web.dto.PaymentOrderDto;
import com.auction.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuctionWinnerToDtoMapper implements Mapper<AuctionWinner, AuctionWinnerDto> {
  private final Mapper<PaymentOrder, PaymentOrderDto> paymentOrderDtoMapper;
  private final Mapper<AuctionEvent, AuctionEventDto> auctionEventDtoMapper;
  private final Mapper<User, UserDto> userUserDtoMapper;


  @Override
  public AuctionWinnerDto map(AuctionWinner entity) {
    return AuctionWinnerDto.builder()
            .price(entity.getPrice())
            .user(userUserDtoMapper.map(entity.getUser()))
            .auctionEvent(auctionEventDtoMapper.map(entity.getAuctionEvent()))
            .paymentOrder(paymentOrderDtoMapper.map(entity.getPaymentOrder()))
            .genDate(entity.getGenDate().toString())
            .status(entity.getStatus())
            .build();
  }
}
