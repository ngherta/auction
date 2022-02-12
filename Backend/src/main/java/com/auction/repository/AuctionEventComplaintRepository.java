package com.auction.repository;

import com.auction.model.AuctionEventComplaint;
import com.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionEventComplaintRepository extends JpaRepository<AuctionEventComplaint, Long> {
  void deleteAllByUser(User user);
}
