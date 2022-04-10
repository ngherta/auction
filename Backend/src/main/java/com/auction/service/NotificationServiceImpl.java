package com.auction.service;

import com.auction.exception.NotificationNotFoundException;
import com.auction.exception.UserNotFoundException;
import com.auction.model.Notification;
import com.auction.model.User;
import com.auction.model.enums.NotificationType;
import com.auction.model.mapper.Mapper;
import com.auction.projection.NotificationProjection;
import com.auction.repository.NotificationRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.NotificationService;
import com.auction.web.dto.NotificationDto;
import com.auction.web.dto.request.NotificationUpdateRequest;
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
  private final UserRepository userRepository;
  private final Mapper<Notification, NotificationDto> notificationDtoMapper;

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

    Notification complaintNotification = Notification
            .builder()
            .notificationType(NotificationType.COMPLAINT_ANSWER)
            .user(user)
            .value(true)
            .build();
    notificationList.add(complaintNotification);

    Notification finishingAuctionNotification = Notification
            .builder()
            .user(user)
            .notificationType(NotificationType.FINISHING_AUCTION)
            .value(true)
            .build();
    notificationList.add(finishingAuctionNotification);

    notificationRepository.saveAll(notificationList);
  }

  @Override
  @Transactional(readOnly = true)
  public List<NotificationProjection> getNotificationTypeByUser(User user) {
    return notificationRepository.findActiveNotificationByUser(user.getId());
  }


  @Override
  @Transactional
  public void deleteByUser(User user) {
    notificationRepository.deleteAllByUser(user);
  }

  @Override
  @Transactional(readOnly = true)
  public List<NotificationDto> getAllByUser(Long userId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User[" + userId + "] not found!"));
    return getAllByUser(user);
  }

  @Override
  @Transactional(readOnly = true)
  public List<NotificationDto> getAllByUser(User user) {
    return notificationDtoMapper.mapList(notificationRepository.findAllByUser(user));
  }

  @Override
  @Transactional
  public void update(NotificationUpdateRequest request) {
    User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new UserNotFoundException("User[" + request.getUserId() + "] not found!"));
    Notification notification = notificationRepository.findAllByNotificationTypeAndUser(request.getType(), user)
            .orElseThrow(() -> new NotificationNotFoundException("Notification [" + request.getType() +
                                                                         "] for user[" + user.getId() + "] not found!"));
    notification.setValue(request.getValue());

  }

}
