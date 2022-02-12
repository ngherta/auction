package com.auction.event.notification.listeners;

import com.auction.event.notification.AuctionCreationNotificationEvent;
import com.auction.service.interfaces.NotificationTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuctionCreationNotificationListener {
  private final NotificationTemplateService notificationTemplateService;

  @Async
  @EventListener(AuctionCreationNotificationEvent.class)
  public void sendNotificationOfCreationAuction(AuctionCreationNotificationEvent event) {
    notificationTemplateService.sendNotificationOfCreatingAuction(event.getAuctionEvent());
  }


}
