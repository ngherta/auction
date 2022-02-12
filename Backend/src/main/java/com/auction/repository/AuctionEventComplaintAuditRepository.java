package com.auction.repository;

import com.auction.model.AuctionEventComplaintAudit;
import com.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionEventComplaintAuditRepository extends JpaRepository<AuctionEventComplaintAudit, Long> {
  void deleteAllByAdmin(User user);
}
