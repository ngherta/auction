package com.auction.web.dto;

import com.auction.model.enums.NotificationType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationMessageDto {
  private Long messageId;
  private NotificationType type;
  private String message;
  private String genDate;
  private Boolean seen;
  private String image;
}
