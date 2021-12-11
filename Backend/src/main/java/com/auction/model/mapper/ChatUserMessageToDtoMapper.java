package com.auction.model.mapper;

import com.auction.model.UserChatMessage;
import com.auction.web.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChatUserMessageToDtoMapper implements Mapper<UserChatMessage, ChatMessageDto>{

  @Override
  public ChatMessageDto map(UserChatMessage entity) {
    return ChatMessageDto.builder()
            .message(entity.getMessage())
            .chatRoom(entity.getUserChat().getId())
            .senderId(entity.getSender().getId())
            .senderFirstName(entity.getSender().getFirstName())
            .senderLastName(entity.getSender().getLastName())
            .build();
  }
}
