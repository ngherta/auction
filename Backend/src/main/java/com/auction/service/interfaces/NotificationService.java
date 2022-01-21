package com.auction.service.interfaces;

import com.auction.model.NotificationMessageUser;
import com.auction.model.User;
import com.auction.projection.NotificationProjection;

import java.util.List;

public interface NotificationService {
  void createDefaultNotificationsForUser(User user);

  List<NotificationProjection> getNotificationTypeByUser(User user);

  void deleteByUser(User user);

  List<NotificationMessageUser> getNotificationsForUser(User user);
}
