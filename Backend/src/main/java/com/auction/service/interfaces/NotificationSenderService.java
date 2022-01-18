package com.auction.service.interfaces;

import com.auction.model.enums.NotificationType;
import com.auction.web.dto.response.notification.NotificationTemplate;

public interface NotificationSenderService {
  <T extends NotificationTemplate> void sendNotificationToUsers(T response, NotificationType notificationType);
}
