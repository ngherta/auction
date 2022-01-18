package com.auction.service;

import com.auction.helper.UserSessionCache;
import com.auction.model.AuctionEvent;
import com.auction.model.NotificationMessage;
import com.auction.model.NotificationMessageUser;
import com.auction.model.User;
import com.auction.model.enums.NotificationType;
import com.auction.projection.NotificationProjection;
import com.auction.repository.NotificationMessageRepository;
import com.auction.repository.NotificationMessageUserRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.NotificationGenerationService;
import com.auction.service.interfaces.NotificationSenderService;
import com.auction.service.interfaces.NotificationService;
import com.auction.web.dto.response.notification.AuctionNotificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationGenerationServiceImpl implements NotificationGenerationService {

  private final NotificationSenderService notificationSenderService;
  private final NotificationService notificationService;
  private final NotificationMessageUserRepository notificationMessageUserRepository;
  private final NotificationMessageRepository notificationMessageRepository;
  private final UserSessionCache userServiceCache;
  private final UserRepository userRepository;


  @Override
  @Transactional
  @Async
  public void sendNotificationOfCreatingAuction(AuctionEvent auctionEvent) {
    NotificationMessage message = NotificationMessage.builder()
            .message(auctionEvent.getUser().getFirstName() + " " + auctionEvent.getUser().getLastName() +
                             " created new auction " + auctionEvent.getTitle())
            .genDate(LocalDateTime.now())
            .type(NotificationType.CREATING_AUCTION)
            .build();

    message = notificationMessageRepository.save(message);
    generateNotificationsForActiveUsers(message);

    AuctionNotificationDto response =
            AuctionNotificationDto.builder()
                    .auctionId(auctionEvent.getId())
                    .build();
    response.setNotificationType(NotificationType.CREATING_AUCTION);
    response.setMessage(auctionEvent.getUser().getFirstName() + " " + auctionEvent.getUser().getLastName() +
                                " created new auction " + auctionEvent.getTitle());
    notificationSenderService.sendNotificationToUsers(response, NotificationType.CREATING_AUCTION);
  }

  @Transactional
  @Override
  public void generateNotificationsForActiveUsers(NotificationMessage message) {
    Set<Long> activeUserIds = userServiceCache.getActiveUsers();
    List<User> activeUsers = userRepository.findAllById(activeUserIds);

    List<NotificationMessageUser> notifications = new ArrayList<>();
    activeUsers.forEach(user -> {
      NotificationMessageUser notificationForUser = NotificationMessageUser.builder()
              .notificationMessage(message)
              .user(user)
              .seen(false)
              .build();
      notifications.add(notificationForUser);
    });
    notificationMessageUserRepository.saveAll(notifications);
    ///SENDER
  }

  @Transactional
  @Override
  public void generateNotificationsForUser(User user) {
    List<NotificationProjection> notificationSettings = notificationService.getNotificationTypeByUser(user);
    List<NotificationType> typeList = notificationSettings.stream()
            .map(NotificationProjection::getNotificationType).collect(Collectors.toList());

    List<NotificationMessage> messages = notificationService.findNotificationMessageForCreateByUser(user, typeList);
    createNotificationMessagesForUser(user, messages);
  }

  @Transactional
  @Override
  public void createNotificationMessagesForUser(User user, List<NotificationMessage> messages) {
    List<NotificationMessageUser> notificationMessageUsers = new ArrayList<>();

    messages.forEach(e -> {
      NotificationMessageUser notification = NotificationMessageUser.builder()
              .notificationMessage(e)
              .seen(false)
              .user(user)
              .build();
      notificationMessageUsers.add(notification);
    });

    notificationMessageUserRepository.saveAll(notificationMessageUsers);
  }
}
