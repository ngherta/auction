package com.auction.model.mapper;

import com.auction.model.Notification;
import com.auction.web.dto.NotificationDto;
import org.springframework.stereotype.Component;

@Component
public class NotificationToDtoMapper implements Mapper<Notification, NotificationDto> {

  @Override
  public NotificationDto map(Notification entity) {
    return NotificationDto
            .builder()
            .notificationType(entity.getNotificationType())
            .userId(entity.getUser().getId())
            .description(entity.getNotificationType().getDescription())
            .name(entity.getNotificationType().getValue())
            .value(entity.getValue())
            .build();
  }
}
