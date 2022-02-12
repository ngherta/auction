package com.auction.service.interfaces;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.web.dto.PaymentOrderDto;
import com.auction.web.dto.PaymentOrderWithAuctionEventDto;
import org.springframework.data.domain.Page;

public interface PaymentService {
  void createPaymentForAuction(AuctionWinner auctionWinner);
  PaymentOrderDto findByAuctionEvent(Long auctionId);

  PaymentOrderDto findByAuctionEvent(AuctionEvent auctionEvent);

  void execute(String paymentId, String payerId);

  Page<PaymentOrderWithAuctionEventDto> findByUser(Long userId, int page, int perPage);
}
