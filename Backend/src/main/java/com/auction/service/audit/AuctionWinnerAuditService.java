package com.auction.service.audit;

import com.auction.model.AuctionWinner;

public interface AuctionWinnerAuditService {
  void create(AuctionWinner auctionWinner);
}
