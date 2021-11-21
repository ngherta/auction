package com.auction.service.interfaces;

import com.auction.model.AuctionAction;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.request.BetRequest;
import com.auction.exception.AuctionEventNotFoundException;

import java.util.List;

public interface AuctionActionService {
  List<AuctionActionDto> bet(BetRequest request);

  List<AuctionActionDto> getAllByAuctionId(Long auctioId);
}
