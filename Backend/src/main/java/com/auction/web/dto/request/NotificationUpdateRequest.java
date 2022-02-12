package com.auction.web.dto.request;

import com.auction.model.enums.NotificationType;
import lombok.Data;

@Data
public class NotificationUpdateRequest {
  private NotificationType type;
  private Boolean value;
  private Long userId;
}
