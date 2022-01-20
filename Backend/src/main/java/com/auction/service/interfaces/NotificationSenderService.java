package com.auction.service.interfaces;

import com.auction.model.NotificationMessage;
import com.auction.model.User;
import com.auction.model.enums.NotificationType;
import com.auction.web.dto.response.notification.NotificationTemplate;

import java.util.List;

public interface NotificationSenderService {
  <T extends NotificationTemplate> void sendNotificationToActiveUsers(T response, NotificationType notificationType);

  void sendNotificationToUsers(NotificationMessage message, List<User> activeUsers);
}
