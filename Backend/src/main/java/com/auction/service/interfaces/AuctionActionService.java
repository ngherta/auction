package com.auction.service.interfaces;

import com.auction.model.AuctionEvent;
import com.auction.web.dto.AuctionActionDto;

import java.util.List;

public interface AuctionActionService {
  AuctionActionDto bet(Double bet, Long auctionId, Long userId);

  List<AuctionActionDto> getAllByAuctionId(Long auctionId);

  void checkBet(AuctionEvent auctionEvent, Double bet);
}
