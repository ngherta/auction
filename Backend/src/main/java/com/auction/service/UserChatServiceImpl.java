package com.auction.service;

import com.auction.exception.ChatRoomNotFoundException;
import com.auction.model.User;
import com.auction.model.UserChat;
import com.auction.model.UserChatMessage;
import com.auction.model.mapper.Mapper;
import com.auction.repository.UserChatMessageRepository;
import com.auction.repository.UserChatRepository;
import com.auction.service.interfaces.UserChatService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.ChatMessageDto;
import com.auction.web.dto.request.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class UserChatServiceImpl implements UserChatService {
  private final UserChatRepository userChatRepository;
  private final UserChatMessageRepository userChatMessageRepository;
  private final UserService userService;
  private final Mapper<UserChatMessage, ChatMessageDto> userChatMessageMapper;

  @Override
  @Transactional
  public ChatMessageDto send(ChatMessageRequest request) {
    UserChat room = userChatRepository.findById(request.getChatRoom())
            .orElseThrow(() -> new ChatRoomNotFoundException("ChatRoom[" + request.getChatRoom() + "] doesn't exist!"));

    User sender = userService.findById(request.getSenderId());

    UserChatMessage message = UserChatMessage.builder()
            .userChat(room)
            .genDate(LocalDateTime.now())
            .build();
    message.setSender(sender);
    message.setMessage(request.getMessage());
    return userChatMessageMapper.map(userChatMessageRepository.save(message));
  }

  @Override
  public List<ChatMessageDto> getAllByChat(UserChat chat) {
    return userChatMessageMapper.mapList(userChatMessageRepository.findByUserChat(chat));
  }
}
