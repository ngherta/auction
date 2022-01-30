package com.auction.service;

import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.ChatRoomNotFoundException;
import com.auction.model.AuctionChat;
import com.auction.model.AuctionChatMessage;
import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionChatMessageRepository;
import com.auction.repository.AuctionChatRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.service.interfaces.AuctionChatService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.ChatMessageDto;
import com.auction.web.dto.request.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@CacheConfig(cacheNames = {"auction-messages"})
class AuctionChatServiceImpl implements AuctionChatService {

  private final AuctionChatRepository auctionChatRepository;
  private final AuctionChatMessageRepository auctionChatMessageRepository;
  private final AuctionEventRepository auctionEventRepository;
  private final UserService userService;
  private final Mapper<AuctionChatMessage, ChatMessageDto> auctionChatMessageMapper;
  private final CacheManager cacheManager;

  @Override
  @Transactional
  public ChatMessageDto send(ChatMessageRequest request, Long auctionId) {
    AuctionEvent auctionEvent = auctionEventRepository
            .findById(auctionId)
            .orElseThrow(() -> new AuctionEventNotFoundException("AuctionEvent[" + auctionId + "] not found!"));
    AuctionChat room = auctionChatRepository.findByAuctionEvent(auctionEvent)
            .orElseThrow(() -> new ChatRoomNotFoundException("ChatRoom for auctionEvent[" + auctionId + "] doesn't exist!"));

    User sender = userService.findById(request.getSenderId());

    AuctionChatMessage message = AuctionChatMessage.builder()
            .auctionChat(room)
            .genDate(LocalDateTime.now())
            .sender(sender)
            .message(request.getMessage())
            .build();
    return auctionChatMessageMapper.map(auctionChatMessageRepository.save(message));
  }

  @Override
  @Transactional
  public void create(AuctionEvent auctionEvent) {
    AuctionChat room = new AuctionChat(auctionEvent);
    auctionChatRepository.save(room);
  }

  @Override
  @Cacheable
  @Transactional(readOnly = true)
  public List<ChatMessageDto> getAllByChat(AuctionChat chat) {
    return auctionChatMessageMapper
            .mapList(auctionChatMessageRepository
                             .findByAuctionChat(chat));
  }

  @Override
  @Transactional(readOnly = true)
  public List<ChatMessageDto> getAllByChatId(Long id) {
    AuctionChat chat = auctionChatRepository
            .findById(id)
            .orElseThrow(() -> new ChatRoomNotFoundException("ChatRoom with id[" + id + "] not found!"));
    return getAllByChat(chat);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ChatMessageDto> getAllByAuctionId(Long id) {
    AuctionEvent auctionEvent = auctionEventRepository
            .findById(id)
            .orElseThrow(
                    () -> new AuctionEventNotFoundException("Auction[" + id + "] not found!"));
    AuctionChat chat = auctionChatRepository.findByAuctionEvent(auctionEvent)
            .orElseThrow(() -> new ChatRoomNotFoundException("ChatRoom for AuctionEvent[ " + id + " ] not found!"));
    return getAllByChat(chat);
  }

  @Override
  @Transactional
  public void deleteByAuction(AuctionEvent auctionEvent) {
    AuctionChat auctionChat = auctionChatRepository.findByAuctionEvent(auctionEvent)
            .orElseThrow(() -> new ChatRoomNotFoundException("AuctionChat for auctionEvent[" + auctionEvent.getId() + "] doesn't exist!"));
    delete(auctionChat);
  }

  @Override
  @Transactional
  public void delete(AuctionChat auctionChat) {
    auctionChatMessageRepository.deleteAllByAuctionChat(auctionChat);
    auctionChatRepository.delete(auctionChat);
  }
}
