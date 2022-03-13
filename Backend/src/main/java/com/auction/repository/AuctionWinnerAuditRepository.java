package com.auction.repository;

import com.auction.model.AuctionWinnerAudit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionWinnerAuditRepository extends JpaRepository<AuctionWinnerAudit, Long> {

}
