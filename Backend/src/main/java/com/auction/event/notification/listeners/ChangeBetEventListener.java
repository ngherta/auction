package com.auction.event.notification.listeners;

import com.auction.event.notification.ChangeBetEvent;
import com.auction.service.interfaces.NotificationTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChangeBetEventListener {

  private final NotificationTemplateService notificationTemplateService;

  @Async
  @EventListener(ChangeBetEvent.class)
  public void sendNotificationOfFinishingAuction(ChangeBetEvent event) {
    notificationTemplateService.sendNotificationOfChangeBet(event.getAction());
  }
}
