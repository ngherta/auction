package com.auction.repository;

import com.auction.web.model.AuctionEventComplaint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionEventComplaintRepository extends JpaRepository<AuctionEventComplaint, Long> {
}
