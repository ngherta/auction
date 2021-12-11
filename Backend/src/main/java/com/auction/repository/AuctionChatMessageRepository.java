package com.auction.repository;

import com.auction.model.AuctionChat;
import com.auction.model.AuctionChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionChatMessageRepository extends JpaRepository<AuctionChatMessage, Long> {
  List<AuctionChatMessage> findByAuctionChat(AuctionChat chat);
}
