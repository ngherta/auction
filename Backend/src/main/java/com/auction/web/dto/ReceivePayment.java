package com.auction.web.dto;

import com.auction.model.enums.PaymentStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReceivePayment {
    private Long id;
    private AuctionEventDto auctionEvent;
    private Double amount;
    private String currency;
    private PaymentStatus status;
    private String genDate;
}
