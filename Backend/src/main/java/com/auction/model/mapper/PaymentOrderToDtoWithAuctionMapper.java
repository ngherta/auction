package com.auction.model.mapper;

import com.auction.model.AuctionEvent;
import com.auction.model.PaymentOrder;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.PaymentOrderWithAuctionEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class PaymentOrderToDtoWithAuctionMapper implements Mapper<PaymentOrder, PaymentOrderWithAuctionEventDto> {

  private final Mapper<AuctionEvent, AuctionEventDto> auctionEventDtoMapper;

  @Override
  public PaymentOrderWithAuctionEventDto map(PaymentOrder entity) {
    return PaymentOrderWithAuctionEventDto.builder()
            .auctionEvent(auctionEventDtoMapper.map(entity.getAuctionEvent()))
            .paymentId(entity.getPaymentId())
            .currency(entity.getCurrency())
            .link(entity.getLink())
            .status(entity.getStatus())
            .price(entity.getPrice())
            .build();
  }
}
