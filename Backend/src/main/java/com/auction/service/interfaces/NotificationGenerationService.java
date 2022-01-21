package com.auction.service.interfaces;

import com.auction.model.AuctionEvent;
import com.auction.model.NotificationMessage;
import com.auction.model.NotificationMessageUser;
import com.auction.model.User;

import java.util.List;

public interface NotificationGenerationService {

  void sendNotificationOfCreatingAuction(AuctionEvent auctionEvent);

  void initNotificationsForUser(Long userId);

  void generateNotificationsForActiveUsers(NotificationMessage message);

  List<NotificationMessageUser> generateInitNotificationsForUser(User user);
}
