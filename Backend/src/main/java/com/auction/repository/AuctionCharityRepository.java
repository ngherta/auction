package com.auction.repository;

import com.auction.web.model.AuctionCharity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionCharityRepository extends JpaRepository<AuctionCharity, Long> {
    AuctionCharity save(AuctionCharity auctionCharity);

    AuctionCharity findByAuctionEvent(Long id);
}
