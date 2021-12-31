package com.auction.repository;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionWinnerRepository extends JpaRepository<AuctionWinner, Long> {
  Optional<AuctionWinner> findByAuctionEvent(AuctionEvent auctionEvent);
}
