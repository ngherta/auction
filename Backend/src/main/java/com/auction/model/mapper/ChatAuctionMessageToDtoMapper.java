package com.auction.model.mapper;

import com.auction.model.AuctionChatMessage;
import com.auction.web.dto.ChatMessageDto;

public class ChatAuctionMessageToDtoMapper implements Mapper<AuctionChatMessage, ChatMessageDto>{
  @Override
  public ChatMessageDto map(AuctionChatMessage entity) {
   return ChatMessageDto.builder()
            .message(entity.getMessage())
            .chatRoom(entity.getAuctionChat().getId())
            .senderId(entity.getSender().getId())
            .senderFirstName(entity.getSender().getFirstName())
            .senderLastName(entity.getSender().getLastName())
            .build();
  }
}
