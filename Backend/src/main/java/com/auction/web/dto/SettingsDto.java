package com.auction.web.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class SettingsDto {
  private List<NotificationDto> notifications;
}
