package com.auction.repository;

import com.auction.web.model.AuctionAction;
import com.auction.web.model.AuctionEvent;
import com.auction.web.model.User;
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

  List<AuctionAction> findByAuctionEvent(AuctionEvent auctionEvent);

  @Query(nativeQuery = true, value =
          "SELECT * FROM auction_action AS aa " +
          "WHERE aa.auction_id = auctionId AND " +
          "aa.user_id != userWinnerId " +
          "GROUP BY aa.user_id ")
  List<AuctionAction> getAllByAuctionGroupByUser(@Param("auctionId") Long auctionId,
                                                 @Param("userWinnerId") Long userWinnerId);

  void deleteAllByAuctionEvent(AuctionEvent auctionEvent);

  void deleteAllByUser(User user);
}
