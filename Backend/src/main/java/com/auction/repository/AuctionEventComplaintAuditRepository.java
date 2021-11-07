package com.auction.repository;

import com.auction.model.AuctionEventComplaintAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionEventComplaintAuditRepository extends JpaRepository<AuctionEventComplaintAudit, Long> {
}
