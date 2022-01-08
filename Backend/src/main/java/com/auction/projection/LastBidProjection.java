package com.auction.projection;

public interface LastBidProjection {
  Double getLastBid();

  Long getAuctionId();
}
