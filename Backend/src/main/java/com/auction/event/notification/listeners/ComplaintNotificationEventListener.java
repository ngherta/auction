package com.auction.event.notification.listeners;

import com.auction.event.notification.ComplaintNotificationEvent;
import com.auction.service.interfaces.NotificationTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ComplaintNotificationEventListener {

  private final NotificationTemplateService notificationTemplateService;

  @Async
  @EventListener(ComplaintNotificationEvent.class)
  public void sendComplaintNotification(ComplaintNotificationEvent event) {
    notificationTemplateService.sendNotificationOfComplaintAnswer(event.getAudit());
  }
}
