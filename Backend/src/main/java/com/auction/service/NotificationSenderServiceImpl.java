package com.auction.service;

import com.auction.helper.UserSessionCache;
import com.auction.exception.NotificationNotFoundException;
import com.auction.model.Notification;
import com.auction.model.enums.NotificationType;
import com.auction.repository.NotificationRepository;
import com.auction.service.interfaces.NotificationSenderService;
import com.auction.web.dto.response.notification.NotificationTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
class NotificationSenderServiceImpl implements NotificationSenderService {

  private final UserSessionCache userServiceCache;
  private final NotificationRepository notificationRepository;
  private final SimpMessagingTemplate messagingTemplate;

  public <T extends NotificationTemplate> void sendNotificationToUsers(T response, NotificationType notificationType) {
    Set<Long> usersActive = userServiceCache.getActiveUsers();
    usersActive.forEach(userId -> {
      Notification notification = notificationRepository.findByUserAndNotificationType(userId, notificationType.name())
              .orElseThrow(() -> new NotificationNotFoundException("Notification [" + notificationType.name()
                                                                           + "] for user [" + userId + "] doesn't exist!"));
      if (Boolean.TRUE.equals(notification.getValue())) {
        messagingTemplate.convertAndSend("/notification/" + userId, response);
      }
    });
  }

}
