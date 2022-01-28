package com.auction.service.interfaces;

import com.auction.model.AuctionChat;
import com.auction.model.AuctionEvent;
import com.auction.web.dto.ChatMessageDto;
import com.auction.web.dto.request.ChatMessageRequest;

import java.util.List;

public interface AuctionChatService {
  ChatMessageDto send(ChatMessageRequest request, Long auctionId);

  void create(AuctionEvent auctionEvent);

  List<ChatMessageDto> getAllByChat (AuctionChat room);

  List<ChatMessageDto> getAllByChatId(Long id);

  List<ChatMessageDto> getAllByAuctionId(Long id);

  void deleteByAuction(AuctionEvent auctionEvent);
}
