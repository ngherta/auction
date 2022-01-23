package com.auction.model.fixture;

import com.auction.model.NotificationMessage;
import com.auction.model.NotificationMessageUser;
import com.auction.model.enums.NotificationType;

import java.time.LocalDateTime;

public class NotificationMessageFixture {

  public static NotificationMessageUser notificationMessageUser() {
    return NotificationMessageUser.builder()
            .seen(false)
            .user(UserFixture.user())
            .notificationMessage(notificationMessage())
            .build();
  }

  public static NotificationMessage notificationMessage() {
    return NotificationMessage.builder()
            .type(NotificationType.NEW_MESSAGE)
            .genDate(LocalDateTime.now())
            .message("test")
            .build();
  }
}
