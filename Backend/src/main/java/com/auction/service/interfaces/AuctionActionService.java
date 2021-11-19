package com.auction.service.interfaces;

import com.auction.dto.AuctionActionDto;
import com.auction.dto.request.BetRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.web.model.AuctionEvent;

import java.util.List;

public interface AuctionActionService {
  List<AuctionActionDto> bet(BetRequest request) throws AuctionEventNotFoundException;

  List<AuctionActionDto> getAllByAuctionId(Long auctioId) throws AuctionEventNotFoundException;
}
