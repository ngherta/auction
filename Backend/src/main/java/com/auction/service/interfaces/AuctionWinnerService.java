package com.auction.service.interfaces;

import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.web.dto.AuctionWinnerDto;

import java.util.List;

public interface AuctionWinnerService {

  List<AuctionWinnerDto> getAllAuctionWinnerForUser(User user);

  List<AuctionWinnerDto> getAllAuctionWinnerForUser(Long userId);

  void createForFinishPrice(AuctionEvent auctionEvent, User user);
}
