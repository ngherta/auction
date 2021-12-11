package com.auction.service.interfaces;

import com.auction.model.AuctionChat;
import com.auction.model.AuctionEvent;
import com.auction.web.dto.ChatMessageDto;
import com.auction.web.dto.request.ChatMessageRequest;

import java.util.List;

public interface AuctionChatService {
  ChatMessageDto send(ChatMessageRequest request);

  void create(AuctionEvent auctionEvent);

  List<ChatMessageDto> getAllByChat (AuctionChat room);
}
