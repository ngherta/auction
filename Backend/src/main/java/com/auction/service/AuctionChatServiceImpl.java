package com.auction.service;

import com.auction.exception.ChatRoomNotFoundException;
import com.auction.model.AuctionChat;
import com.auction.model.AuctionChatMessage;
import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionChatMessageRepository;
import com.auction.repository.AuctionChatRepository;
import com.auction.service.interfaces.AuctionChatService;
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
class AuctionChatServiceImpl implements AuctionChatService {

  private final AuctionChatRepository auctionChatRepository;
  private final AuctionChatMessageRepository auctionChatMessageRepository;
  private final UserService userService;
  private final Mapper<AuctionChatMessage, ChatMessageDto> auctionChatMessageMapper;

  @Override
  @Transactional
  public ChatMessageDto send(ChatMessageRequest request) {
    AuctionChat room = auctionChatRepository.findById(request.getChatRoom())
            .orElseThrow(() -> new ChatRoomNotFoundException("ChatRoom[" + request.getChatRoom() + "] doesn't exist!"));

    User sender = userService.findById(request.getSenderId());

    AuctionChatMessage message = AuctionChatMessage.builder()
            .auctionChat(room)
            .genDate(LocalDateTime.now())
            .build();
    message.setSender(sender);
    message.setMessage(request.getMessage());
    return auctionChatMessageMapper.map(auctionChatMessageRepository.save(message));
  }

  @Override
  @Transactional
  public void create(AuctionEvent auctionEvent) {
    AuctionChat room = new AuctionChat(auctionEvent);
    auctionChatRepository.save(room);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ChatMessageDto> getAllByChat(AuctionChat chat) {
    return auctionChatMessageMapper.mapList(auctionChatMessageRepository.findByAuctionChat(chat));
  }

  @Override
  @Transactional
  public void deleteByAuction(AuctionEvent auctionEvent) {
    AuctionChat auctionChat = auctionChatRepository.findByAuctionEvent(auctionEvent)
            .orElseThrow(() -> new ChatRoomNotFoundException("AuctionChat for auctionEvent[" + auctionEvent.getId() + "] doesn't exist!"));
    auctionChatRepository.delete(auctionChat);
  }
}
