package com.auction.web.dto.response.notification;

import com.auction.model.enums.NotificationType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuctionCreatingNotificationDto {
  private NotificationType notificationType;
  private Long auctionId;
  private String message;
}
