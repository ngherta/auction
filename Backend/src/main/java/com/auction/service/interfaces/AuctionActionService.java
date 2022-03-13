package com.auction.service.interfaces;

import com.auction.model.AuctionAction;
import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.projection.LastBidProjection;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.response.LastBidResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AuctionActionService {
  AuctionActionDto bet(Double bet, Long auctionId, Long userId);

  List<AuctionActionDto> getAllByAuctionId(Long auctionId);

  void checkBet(AuctionEvent auctionEvent, Double bet);

  void checkBet(AuctionEvent auctionEvent, Double bet, Optional<AuctionAction> auctionAction);

  List<LastBidResponse> getLastBidForAuction(List<Long> listOfAuctionsIds);

  List<LastBidProjection> getLasBidsFromId(List<Long> auctionIds);

  Page<AuctionEventDto> getAuctionsByParticipant(Long userId, int page, int perPage);

  void bet(String terminal);

  AuctionActionDto defaultBet(AuctionEvent currentAuctionEvent, User user);
}
