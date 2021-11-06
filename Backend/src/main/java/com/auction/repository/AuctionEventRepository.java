package com.auction.repository;

import com.auction.model.AuctionEvent;
import com.auction.model.User;
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
            "select a.id, " +
            "(select count(*) from auction_action aa where aa.auction_id = a.id) as count " +
            "from auction_table a " +
            "order by count")
    List<Object[]> getAuctionEventForSorting();

    @Query(nativeQuery = true, value =
            "select a.* from auction_table as a " +
            "left join auction_sort as aa on a.id = aa.auction_id " +
            "order by aa.rating")
    List<AuctionEvent> getAuctionEventByRating();

    List<AuctionEvent> findByUser(User user);
}
