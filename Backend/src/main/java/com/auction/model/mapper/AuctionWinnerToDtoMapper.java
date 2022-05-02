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

import java.text.DecimalFormat;
import java.time.format.DateTimeFormatter;

@Component
@RequiredArgsConstructor
class AuctionWinnerToDtoMapper implements Mapper<AuctionWinner, AuctionWinnerDto> {
  private final Mapper<PaymentOrder, PaymentOrderDto> paymentOrderDtoMapper;
  private final Mapper<AuctionEvent, AuctionEventDto> auctionEventDtoMapper;
  private final Mapper<User, UserDto> userUserDtoMapper;


  @Override
  public AuctionWinnerDto map(AuctionWinner entity) {
    return AuctionWinnerDto.builder()
        .price(new DecimalFormat("#0.00").format(entity.getPrice()))
        .user(userUserDtoMapper.map(entity.getUser()))
        .auctionEvent(auctionEventDtoMapper.map(entity.getAuctionEvent()))
        .paymentOrder(paymentOrderDtoMapper.map(entity.getPaymentOrder()))
        .genDate(entity.getGenDate().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")))
        .status(entity.getStatus())
        .address(entity.getAddress())
        .city(entity.getCity())
        .country(entity.getCountry())
        .hasDefaultAddress(entity.getUser().hasDefaultAddress())
        .needAddress(!entity.hasAddress())
        .trackNumber(entity.getTrackNumber())
        .build();
  }
}
