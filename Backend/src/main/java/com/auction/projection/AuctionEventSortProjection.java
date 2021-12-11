package com.auction.projection;

import java.math.BigInteger;

public interface AuctionEventSortProjection {
  BigInteger getCount();

  BigInteger getAuctionId();
}
