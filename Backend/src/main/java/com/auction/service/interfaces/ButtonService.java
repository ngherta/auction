package com.auction.service.interfaces;

import com.auction.model.Button;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.request.CreateButtonRequest;

public interface ButtonService {
  Button findByButtonId(String buttonId);

  void add(CreateButtonRequest request);

  void connect(Long userId, Long auctionId);

  void disconnect(Long userId);

  AuctionActionDto defaultBet(String buttonId);

  AuctionActionDto finishAuction(String buttonId);

  Boolean isConnected(Long userId, Long auctionId);
}
