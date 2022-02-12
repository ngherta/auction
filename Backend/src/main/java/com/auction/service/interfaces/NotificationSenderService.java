package com.auction.service.interfaces;

import com.auction.model.NotificationMessageUser;

import java.util.List;

public interface NotificationSenderService {
  void sendNotificationToUsers(List<NotificationMessageUser> messages);
}
