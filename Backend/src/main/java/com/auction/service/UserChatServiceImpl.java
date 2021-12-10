package com.auction.service;

import com.auction.exception.ChatRoomNotFoundException;
import com.auction.model.User;
import com.auction.model.UserChat;
import com.auction.model.UserChatMessage;
import com.auction.repository.UserChatMessageRepository;
import com.auction.repository.UserChatRepository;
import com.auction.service.interfaces.UserChatService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.request.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
class UserChatServiceImpl implements UserChatService {
  private final UserChatRepository userChatRepository;
  private final UserChatMessageRepository userChatMessageRepository;
  private final UserService userService;

  @Override
  @Transactional
  public void send(ChatMessageRequest request) {
    UserChat room = userChatRepository.findById(request.getChatRoom())
            .orElseThrow(() -> new ChatRoomNotFoundException("ChatRoom[" + request.getChatRoom() + "] doesn't exist!"));

    User sender = userService.findById(request.getSenderId());

    UserChatMessage message = UserChatMessage.builder()
            .userChat(room)
            .message(request.getMessage())
            .sender(sender)
            .build();
    message = userChatMessageRepository.save(message);
  }
}
