package com.auction.repository;

import com.auction.model.AuctionEventSort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionEventSortRepository extends JpaRepository<AuctionEventSort, Long> {
  void deleteAllByAuctionEvent(Long auctionId);
}
