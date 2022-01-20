package com.auction.service;

import com.auction.helper.UserSessionCache;
import com.auction.exception.NotificationNotFoundException;
import com.auction.model.Notification;
import com.auction.model.NotificationMessage;
import com.auction.model.User;
import com.auction.model.enums.NotificationType;
import com.auction.model.mapper.Mapper;
import com.auction.repository.NotificationRepository;
import com.auction.service.interfaces.NotificationSenderService;
import com.auction.web.dto.NotificationMessageDto;
import com.auction.web.dto.response.notification.NotificationTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
class NotificationSenderServiceImpl implements NotificationSenderService {

  private final UserSessionCache userServiceCache;
  private final NotificationRepository notificationRepository;
  private final SimpMessagingTemplate messagingTemplate;
  private final Mapper<NotificationMessage, NotificationMessageDto> notificationMessageDtoMapper;

  public <T extends NotificationTemplate> void sendNotificationToActiveUsers(T response, NotificationType notificationType) {
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

  @Override
  public void sendNotificationToUsers(NotificationMessage message, List<User> activeUsers) {
    NotificationMessageDto dto = notificationMessageDtoMapper.map(message);
    activeUsers.forEach(user -> messagingTemplate.convertAndSend("/notification/" + user.getId(), dto));
  }

}
