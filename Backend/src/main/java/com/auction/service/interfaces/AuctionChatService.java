package com.auction.service.interfaces;

import com.auction.web.dto.request.ChatMessageRequest;

public interface AuctionChatService {
  void send(ChatMessageRequest request);

}
