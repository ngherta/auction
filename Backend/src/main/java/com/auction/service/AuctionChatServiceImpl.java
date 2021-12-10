package com.auction.service;

import com.auction.repository.AuctionChatMessageRepository;
import com.auction.repository.AuctionChatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
class AuctionChatServiceImpl {
  private final AuctionChatRepository auctionChatRepository;
  private final AuctionChatMessageRepository auctionChatMessageRepository;
}
