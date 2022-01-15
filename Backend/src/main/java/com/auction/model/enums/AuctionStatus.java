package com.auction.model.enums;

import java.util.List;
import java.util.stream.Collectors;

public enum AuctionStatus {
  ACTIVE, EXPECTATION, BLOCKED, FINISHED;

  public static List<AuctionStatus> from(List<String> list) {
    return list.stream().map(AuctionStatus::valueOf)
            .collect(Collectors.toList());
  }
}
