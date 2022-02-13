package com.auction.web.dto;

import com.auction.model.enums.NotificationType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {
  private NotificationType notificationType;
  private String name;
  private String description;
  private Boolean value;
  private Long userId;
}
