package com.auction.service.interfaces;

import com.auction.model.AuctionAction;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.request.BetRequest;
import com.auction.exception.AuctionEventNotFoundException;

import java.util.List;

public interface AuctionActionService {
  List<AuctionAction> bet(BetRequest request) throws AuctionEventNotFoundException;

  List<AuctionAction> getAllByAuctionId(Long auctioId) throws AuctionEventNotFoundException;
}
