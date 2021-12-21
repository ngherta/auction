package com.auction.service.interfaces;

import com.auction.model.AuctionEvent;

public interface NotificationSenderService {
  void sendNotificationOfCreatingAuction(AuctionEvent auctionEvent);
}
