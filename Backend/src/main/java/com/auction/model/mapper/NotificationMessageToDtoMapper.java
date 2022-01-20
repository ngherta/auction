package com.auction.model.mapper;

import com.auction.model.NotificationMessage;
import com.auction.web.dto.NotificationMessageDto;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

@Component
public class NotificationMessageToDtoMapper implements Mapper<NotificationMessage, NotificationMessageDto> {

  @Override
  public NotificationMessageDto map(NotificationMessage entity) {
    return NotificationMessageDto.builder()
            .message(entity.getMessage())
            .type(entity.getType())
            .genDate(entity.getGenDate().format(DateTimeFormatter.ofPattern("dd-MM-yy")))
            .build();
  }
}
