package com.auction.projection;

import com.auction.model.enums.AuctionStatus;

import java.time.LocalDateTime;

public interface AuctionSearchProjection {
    Long getId();

    String getTitle();

    LocalDateTime getStartDate();

    LocalDateTime getFinishDate();

    AuctionStatus getStatus();
}
