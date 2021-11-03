package com.auction.service.interfaces;

import com.auction.dto.AuctionActionDto;
import com.auction.dto.request.BetRequest;
import com.auction.model.AuctionAction;
import com.auction.model.AuctionEvent;

import java.util.List;

public interface AuctionActionService {
  List<AuctionActionDto> bet(BetRequest request);

  List<AuctionActionDto> getAllByAuction(AuctionEvent auctionEvent);
}