package com.auction.service.interfaces;

import com.auction.model.AuctionEvent;
import com.auction.model.NotificationMessage;
import com.auction.model.User;

public interface NotificationGenerationService {

  void sendNotificationOfCreatingAuction(AuctionEvent auctionEvent);

  void generateNotificationsForUser(User user);

  void generateNotificationsForActiveUsers(NotificationMessage message);
}
