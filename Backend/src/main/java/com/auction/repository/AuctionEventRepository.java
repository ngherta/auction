package com.auction.repository;

import com.auction.model.AuctionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface AuctionEventRepository extends JpaRepository<AuctionEvent, Long> {

    AuctionEvent save(AuctionEvent auctionEvent);

    Optional<AuctionEvent> findById(Long id);

    @Query(nativeQuery = true, value =
            "select * from auction_table as a " +
            "where a.status = 'ACTIVE' and a.start_date > a.finish_date")
    List<AuctionEvent> getListForChangeStatus();

    @Query(nativeQuery = true, value =
            "SELECT a.*, 1.0 FROM auction_table AS a " +
            "ORDER BY COUNT(SELECT 1 FROM auction_action aa WHERE aa.auction_id = a.id)")
    Map<AuctionEvent, Double> getAuctionEventForSorting();
}
