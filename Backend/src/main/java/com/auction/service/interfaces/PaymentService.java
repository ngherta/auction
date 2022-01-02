package com.auction.service.interfaces;

import com.auction.model.AuctionWinner;

public interface PaymentService {
  void createPaymentForAuction(AuctionWinner auctionWinner);

  void execute(String paymentId, String payerId);
}
