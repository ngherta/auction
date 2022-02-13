package com.auction.service.interfaces;

import com.auction.model.NotificationMessage;
import com.auction.model.NotificationMessageUser;
import com.auction.model.User;
import com.auction.model.enums.NotificationType;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationMessageService {

  List<NotificationMessage> findNotificationMessageForCreateByUser(User user, List<NotificationType> notificationTypes);

  List<NotificationMessageUser> createNotificationMessagesForUser(User user, List<NotificationMessage> messages);

  void deleteOldMessage();

  List<NotificationMessage> getMessagesOlderThan(LocalDateTime localDateTime);

  List<NotificationMessageUser> getNotificationsForUser(User user);

  void seen(Long userId, List<Long> notificationMessageId);

  NotificationMessageUser getNotificationMessageByUserAndMessage(User user, NotificationMessage notificationMessage);

  void seen(Long userId, Long notificationMessageId);
}
