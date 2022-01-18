package com.auction.web.dto.response.notification;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuctionNotificationDto extends NotificationTemplate{
  private Long auctionId;
}
