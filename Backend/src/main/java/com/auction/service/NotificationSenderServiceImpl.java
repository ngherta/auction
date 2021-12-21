package com.auction.service;

import com.auction.cache.UserSessionCache;
import com.auction.exception.NotificationNotFoundException;
import com.auction.model.AuctionEvent;
import com.auction.model.Notification;
import com.auction.model.User;
import com.auction.model.enums.NotificationType;
import com.auction.repository.NotificationRepository;
import com.auction.service.interfaces.NotificationSenderService;
import com.auction.web.dto.response.notification.AuctionCreatingNotificationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
class NotificationSenderServiceImpl implements NotificationSenderService {

  private final static String USER_DESTINATION = "/secured/user";

  private final UserSessionCache userServiceCache;
  private final NotificationRepository notificationRepository;
  private final SimpMessagingTemplate messagingTemplate;

  @Override
  @Transactional(readOnly = true)
  @Async
  public void sendNotificationOfCreatingAuction(AuctionEvent auctionEvent) {
    Set<String> usersActive = userServiceCache.getActiveUsers();
    AuctionCreatingNotificationDto response =
            AuctionCreatingNotificationDto.builder()
                    .notificationType(NotificationType.CREATING_AUCTION)
                    .auctionId(auctionEvent.getId())
                    .message(auctionEvent.getUser().getFirstName() + " " + auctionEvent.getUser().getLastName() +
                            " created new auction "  + auctionEvent.getTitle())
                    .build();

    usersActive.forEach(userId -> {
      Notification notification = notificationRepository.findByUserAndNotificationType(Long.valueOf(userId), NotificationType.CREATING_AUCTION.name())
              .orElseThrow(() -> new NotificationNotFoundException("Notification [" + NotificationType.CREATING_AUCTION.name()
                                                                           + "] for user [" + userId + "] doesn't exist!"));
      if (Boolean.TRUE.equals(notification.getValue())) {
        messagingTemplate.convertAndSendToUser(userId, USER_DESTINATION, response);
//        messagingTemplate.convertAndSend("/notification", response);
      }
    });
  }

  @Transactional
  public void sendNotificationOfFinishAuction(AuctionEvent auctionEvent, List<User> usersList) {

  }

}
