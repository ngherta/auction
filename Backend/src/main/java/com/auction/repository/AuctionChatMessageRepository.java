package com.auction.repository;

import com.auction.model.AuctionChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionChatMessageRepository extends JpaRepository<AuctionChatMessage, Long> {
}
