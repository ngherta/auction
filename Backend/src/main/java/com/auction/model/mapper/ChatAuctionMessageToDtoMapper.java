package com.auction.model.mapper;

import com.auction.model.AuctionChatMessage;
import com.auction.web.dto.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
@Component
public class ChatAuctionMessageToDtoMapper implements Mapper<AuctionChatMessage, ChatMessageDto> {
  @Override
  public ChatMessageDto map(AuctionChatMessage entity) {
    return ChatMessageDto.builder()
            .message(entity.getMessage())
            .chatRoom(entity.getAuctionChat().getId())
            .senderId(entity.getSender().getId())
            .senderFirstName(entity.getSender().getFirstName())
            .senderLastName(entity.getSender().getLastName())
            .genDate(entity.getGenDate().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")))
            .build();
  }
}
