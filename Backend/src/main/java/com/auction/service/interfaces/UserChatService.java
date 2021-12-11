package com.auction.service.interfaces;

import com.auction.model.UserChat;
import com.auction.web.dto.ChatMessageDto;
import com.auction.web.dto.request.ChatMessageRequest;

import java.util.List;

public interface UserChatService {
  ChatMessageDto send(ChatMessageRequest request);

  List<ChatMessageDto> getAllByChat(UserChat chat);
}
