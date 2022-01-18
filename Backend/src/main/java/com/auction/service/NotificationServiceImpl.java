package com.auction.service;

import com.auction.model.Notification;
import com.auction.model.NotificationMessage;
import com.auction.model.User;
import com.auction.model.enums.NotificationType;
import com.auction.projection.NotificationProjection;
import com.auction.repository.NotificationMessageRepository;
import com.auction.repository.NotificationRepository;
import com.auction.service.interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
class NotificationServiceImpl implements NotificationService {

  private final NotificationRepository notificationRepository;
  private final NotificationMessageRepository notificationMessageRepository;

  @Override
  @Transactional
  public void createDefaultNotificationsForUser(User user) {
    List<Notification> notificationList = new ArrayList<>();
    Notification auctionNotification = Notification.builder()
            .user(user)
            .notificationType(NotificationType.CREATING_AUCTION)
            .value(true)
            .build();
    notificationList.add(auctionNotification);

    Notification betChangeNotification = Notification.builder()
            .user(user)
            .notificationType(NotificationType.BET_CHANGED)
            .value(true)
            .build();
    notificationList.add(betChangeNotification);

    notificationRepository.saveAll(notificationList);
  }

  @Override
  @Transactional(readOnly = true)
  public List<NotificationProjection> getNotificationTypeByUser(User user) {
    return notificationRepository.findActiveNotificationByUser(user.getId());
  }

  @Override
  @Transactional
  public List<NotificationMessage> findNotificationMessageForCreateByUser(User user, List<NotificationType> notificationTypes) {
    List<String> list = notificationTypes.stream().map(Enum::name).collect(Collectors.toList());
    return notificationMessageRepository.findUncreatedNotificationForUser(user.getId(), list);
  }

  @Override
  @Transactional
  public void deleteByUser(User user) {
    notificationRepository.deleteAllByUser(user);
  }
}
