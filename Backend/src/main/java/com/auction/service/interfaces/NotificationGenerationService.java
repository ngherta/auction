package com.auction.service.interfaces;

import com.auction.model.NotificationMessage;
import com.auction.model.NotificationMessageUser;
import com.auction.model.User;

import java.util.List;

public interface NotificationGenerationService {

  void initNotificationsForUser(Long userId);

  void generateNotificationsForActiveUsers(NotificationMessage message);

  List<NotificationMessageUser> generateInitNotificationsForUser(User user);

  void generateSingleNotificationFor(User user, NotificationMessage notificationMessage);

  void generateSingleNotificationFor(List<User> users, NotificationMessage notificationMessage);

  void generateNotificationForAdmins(NotificationMessage notificationMessage);
}
