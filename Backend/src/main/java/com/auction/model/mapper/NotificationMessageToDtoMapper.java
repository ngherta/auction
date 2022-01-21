package com.auction.model.mapper;

import com.auction.model.NotificationMessage;
import com.auction.model.NotificationMessageUser;
import com.auction.web.dto.NotificationMessageDto;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class NotificationMessageToDtoMapper implements Mapper<NotificationMessageUser, NotificationMessageDto> {

  @Override
  public NotificationMessageDto map(NotificationMessageUser entity) {
    return NotificationMessageDto.builder()
            .message(entity.getNotificationMessage().getMessage())
            .type(entity.getNotificationMessage().getType())
            .genDate(entity.getNotificationMessage().getGenDate().format(DateTimeFormatter.ofPattern("dd-MM-yy")))
            .seen(entity.getSeen())
            .build();
  }
}
