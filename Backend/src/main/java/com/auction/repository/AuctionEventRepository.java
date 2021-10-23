package com.auction.repository;

import com.auction.model.AuctionEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuctionEventRepository extends JpaRepository<AuctionEvent, Long> {

    AuctionEvent save(AuctionEvent auctionEvent);

    Optional<AuctionEvent> findById(Long id);

    @Query(nativeQuery = true, value =
            "select a.id from auction_table as a " +
            "where a.status = 'ACTIVE' and a.start_date > a.finish_date")
    List<Long> getListForChangeStatus();
}
