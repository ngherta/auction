package com.auction.service.interfaces;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.PaymentOrder;
import com.auction.web.dto.PaymentOrderDto;
import com.auction.web.dto.PaymentOrderWithAuctionEventDto;
import com.auction.web.dto.ReceivePayment;
import org.springframework.data.domain.Page;

public interface PaymentService {
    PaymentOrder createPaymentForAuction(AuctionWinner auctionWinner);

    PaymentOrderDto findByAuctionEvent(Long auctionId);

    PaymentOrderDto findByAuctionEvent(AuctionEvent auctionEvent);

    void execute(String paymentId, String payerId);

    void createAudit(PaymentOrder paymentOrder);

    Double takeCommission(PaymentOrder paymentOrder);

    Page<ReceivePayment> findReceivePaymentsByUser(final Long userId,
                                                   final int page,
                                                   final int perPage);

    Page<PaymentOrderWithAuctionEventDto> findByUser(Long userId, int page, int perPage);

  void confirmPayment(AuctionWinner auctionWinner);
}
