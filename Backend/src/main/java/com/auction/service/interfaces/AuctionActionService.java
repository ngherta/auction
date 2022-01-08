package com.auction.service.interfaces;

import com.auction.model.AuctionEvent;
import com.auction.projection.LastBidProjection;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.response.LastBidResponse;

import java.util.List;

public interface AuctionActionService {
  AuctionActionDto bet(Double bet, Long auctionId, Long userId);

  List<AuctionActionDto> getAllByAuctionId(Long auctionId);

  void checkBet(AuctionEvent auctionEvent, Double bet);

  List<LastBidResponse> getLastBidForAuction(List<Long> listOfAuctionsIds);

  List<LastBidProjection> getLasBidsFromId(List<Long> auctionIds);
}
