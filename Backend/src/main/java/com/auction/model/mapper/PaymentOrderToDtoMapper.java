package com.auction.model.mapper;

import com.auction.model.PaymentOrder;
import com.auction.web.dto.PaymentOrderDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PaymentOrderToDtoMapper implements Mapper<PaymentOrder, PaymentOrderDto> {

  @Override
  public PaymentOrderDto map(PaymentOrder entity) {
    return PaymentOrderDto.builder()
            .paymentId(entity.getPaymentId())
            .currency(entity.getCurrency())
            .auctionId(entity.getAuctionEvent().getId())
            .link(entity.getLink())
            .status(entity.getStatus())
            .price(entity.getPrice())
            .build();
  }
}
