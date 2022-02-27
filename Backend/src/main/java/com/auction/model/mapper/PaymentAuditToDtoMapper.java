package com.auction.model.mapper;

import com.auction.model.AuctionEvent;
import com.auction.model.PaymentAudit;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.ReceivePayment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class PaymentAuditToDtoMapper implements Mapper<PaymentAudit, ReceivePayment>{
    private final Mapper<AuctionEvent, AuctionEventDto> auctionEventDtoMapper;

    @Override
    public ReceivePayment map(PaymentAudit entity) {
        return ReceivePayment.builder()
                .auctionEvent(auctionEventDtoMapper.map(entity.getPaymentOrder().getAuctionEvent()))
                .currency(entity.getCurrency())
                .id(entity.getId())
                .status(entity.getPaymentOrder().getStatus())
                .genDate(entity.getGenDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")))
                .amount(entity.getAmount())
                .build();
    }
}
