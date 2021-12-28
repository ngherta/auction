package com.auction.repository;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionEventSort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuctionEventSortRepository extends JpaRepository<AuctionEventSort, Long> {

  void deleteAllByAuctionEvent(AuctionEvent auctionEvent);

  @Query(nativeQuery = true, value = "" +
          "select * from auction_sort as aes " +
          "where aes.auction_id = :auctionId ")
  Optional<AuctionEventSort> findByAuctionEventId(@Param("auctionId") Long auctionId);
}
