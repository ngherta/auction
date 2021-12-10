package com.auction.service.interfaces;

import com.auction.web.dto.request.ChatMessageRequest;

public interface UserChatService {
  void send(ChatMessageRequest request);
}
