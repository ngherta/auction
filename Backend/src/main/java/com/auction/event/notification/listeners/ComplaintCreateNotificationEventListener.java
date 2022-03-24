package com.auction.event.notification.listeners;

import com.auction.event.notification.ComplaintCreateNotificationEvent;
import com.auction.service.interfaces.NotificationTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ComplaintCreateNotificationEventListener {

  private final NotificationTemplateService notificationTemplateService;

  @Async
  @EventListener(ComplaintCreateNotificationEvent.class)
  public void sendNotificationOfCreatingComplaint(ComplaintCreateNotificationEvent event) {
    notificationTemplateService.sendNotificationOfCreatingComplaint(event.getComplaint());
  }
}
