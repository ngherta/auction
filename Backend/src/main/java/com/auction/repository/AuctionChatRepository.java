package com.auction.repository;

import com.auction.model.AuctionChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionChatRepository extends JpaRepository<AuctionChat, Long> {
}
