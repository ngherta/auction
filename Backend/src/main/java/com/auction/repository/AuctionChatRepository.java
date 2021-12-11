package com.auction.repository;

import com.auction.model.AuctionChat;
import com.auction.model.AuctionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionChatRepository extends JpaRepository<AuctionChat, Long> {

  Optional<AuctionChat> findByAuctionEvent(AuctionEvent auctionEvent);
}
