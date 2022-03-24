package com.auction.web.dto.request;

import lombok.Data;

@Data
public class IotConnectionRequest {
  private Long userId;
  private Long auctionId;
}
