package com.auction.repository;

import com.auction.model.AuctionAction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionActionRepository extends JpaRepository<AuctionAction, Long> {

  @Query(nativeQuery = true, value =
          "SELECT * FROM auction_action AS aa " +
          "WHERE aa.auctionEvent = auctionId " +
          "ORDER BY aa.bet DESC " +
          "LIMIT 1")
  AuctionAction getWinnerAuctionAction(@Param("auctionId")Long auctionId);

  List<AuctionAction> findByAuctionEvent(Long auctionId);

  void deleteAllByAuctionEvent(Long auctionId);
}
