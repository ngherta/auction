package com.auction.repository;

import com.auction.web.model.AuctionWinner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionWinnerRepository extends JpaRepository<AuctionWinner, Long> {
  AuctionWinner findByAuctionEvent(Long auctionId);
}
