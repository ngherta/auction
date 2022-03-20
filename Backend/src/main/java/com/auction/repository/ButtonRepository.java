package com.auction.repository;

import com.auction.model.AuctionEvent;
import com.auction.model.Button;
import com.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ButtonRepository extends JpaRepository<Button, Long> {
  Optional<Button> findByCurrentAuctionEvent(AuctionEvent auctionEvent);

  Optional<Button> findByUser(User user);

  Optional<Button> findByUserId(Long userId);

  Optional<Button> findByButtonId(String buttonId);
}
