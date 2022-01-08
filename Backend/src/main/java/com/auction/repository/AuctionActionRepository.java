package com.auction.repository;

import com.auction.model.AuctionAction;
import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.projection.LastBidProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionActionRepository extends JpaRepository<AuctionAction, Long> {

  Optional<AuctionAction> findTopByAuctionEventOrderByBetDesc(AuctionEvent auctionEvent);

  List<AuctionAction> findByAuctionEvent(AuctionEvent auctionEvent);

  @Query(nativeQuery = true, value =
          "SELECT * FROM auction_action AS aa " +
          "WHERE aa.auction_id = :auctionId AND " +
          "aa.user_id != :userWinnerId " +
          "GROUP BY aa.user_id," +
                  " aa.id ")
  List<AuctionAction> getAllByAuctionGroupByUser(@Param("auctionId") Long auctionId,
                                                 @Param("userWinnerId") Long userWinnerId);

  @Query(nativeQuery = true, value = "" +
          "SELECT MAX(aa.bet) AS lastBid, " +
          "aa.auction_id AS auctionId " +
          "FROM auction_action aa " +
          "WHERE aa.auction_id in :auctionIds " +
          "GROUP BY aa.auction_id ")
  List<LastBidProjection> getLastBidByAuctionIds(List<Long> auctionIds);

  void deleteAllByAuctionEvent(AuctionEvent auctionEvent);

  void deleteAllByUser(User user);
}
