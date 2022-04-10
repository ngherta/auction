package com.auction.web.dto.response.notification;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
public class AuctionNotificationDto extends NotificationTemplate{
  private Long auctionId;
}
