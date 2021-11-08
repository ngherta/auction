package com.auction.repository;

import com.auction.web.model.AuctionEvent;
import com.auction.web.model.AuctionEventImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AuctionEventImgRepository extends JpaRepository<AuctionEventImg, Long> {

  List<AuctionEventImg> findByAuctionEvent(AuctionEvent auctionEvent);

  void deleteAllByAuctionEvent(AuctionEvent auctionEvent);

}