package com.auction.web.dto.response.notification;

import com.auction.model.enums.NotificationType;
import lombok.Data;

@Data
public class NotificationTemplate {
  private String message;
  private NotificationType notificationType;
}
