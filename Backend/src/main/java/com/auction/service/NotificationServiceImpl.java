package com.auction.service;

import com.auction.model.Notification;
import com.auction.model.User;
import com.auction.model.enums.NotificationType;
import com.auction.projection.NotificationProjection;
import com.auction.repository.NotificationRepository;
import com.auction.service.interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;

  @Override
  @Transactional
  public void createDefaultNotificationsForUser(User user) {
    List<Notification> notificationList = new ArrayList<>();
    Notification notification = Notification.builder()
            .user(user)
            .notificationType(NotificationType.CREATING_AUCTION)
            .value(true)
            .build();
    notificationList.add(notification);

    notificationRepository.saveAll(notificationList);
  }

  @Override
  @Transactional(readOnly = true)
  public List<NotificationProjection> getNotificationTypeByUser(User user) {
     return notificationRepository.findActiveNotificationByUser(user.getId());
  }

  @Override
  public void deleteByUser(User user) {
    notificationRepository.deleteAllByUser(user);
  }
}
