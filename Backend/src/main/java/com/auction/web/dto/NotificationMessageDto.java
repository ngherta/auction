package com.auction.web.dto;

import com.auction.model.enums.NotificationType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationMessageDto {
  private NotificationType type;
  private String message;
  private String genDate;
}
