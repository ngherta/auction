package com.auction.service.interfaces;

import com.auction.model.AuctionEvent;
import com.auction.model.NotificationMessage;
import com.auction.model.User;

import java.util.List;

public interface NotificationGenerationService {

  void sendNotificationOfCreatingAuction(AuctionEvent auctionEvent);

  void generateNotificationsForUser(User user);

  void createNotificationMessagesForUser(User user, List<NotificationMessage> messages);

  void generateNotificationsForActiveUsers(NotificationMessage message);
}
