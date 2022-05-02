package com.auction.model.mapper;

import com.auction.model.UserChatMessage;
import com.auction.web.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
class ChatUserMessageToDtoMapper implements Mapper<UserChatMessage, ChatMessageDto>{

  @Override
  public ChatMessageDto map(UserChatMessage entity) {
    return ChatMessageDto.builder()
            .message(entity.getMessage())
            .chatRoom(entity.getUserChat().getId())
            .senderId(entity.getSender().getId())
            .senderFirstName(entity.getSender().getFirstName())
            .senderLastName(entity.getSender().getLastName())
            .genDate(entity.getGenDate().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")))
            .build();
  }
}
