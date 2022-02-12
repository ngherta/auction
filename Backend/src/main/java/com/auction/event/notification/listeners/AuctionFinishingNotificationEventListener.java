package com.auction.event.notification.listeners;

import com.auction.event.notification.AuctionFinishingNotificationEvent;
import com.auction.service.interfaces.NotificationTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuctionFinishingNotificationEventListener {

  private final NotificationTemplateService notificationTemplateService;

  @Async
  @EventListener(AuctionFinishingNotificationEvent.class)
  public void sendNotificationOfFinishingAuction(AuctionFinishingNotificationEvent event) {
    notificationTemplateService.sendNotificationOfFinishingAuction(event.getAuctionWinner());
  }
}
