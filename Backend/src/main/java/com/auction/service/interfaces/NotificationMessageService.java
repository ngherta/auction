package com.auction.service.interfaces;

import com.auction.model.NotificationMessage;
import com.auction.model.NotificationMessageUser;
import com.auction.model.User;
import com.auction.model.enums.NotificationType;
import com.auction.web.dto.NotificationMessageDto;

import java.util.List;

public interface NotificationMessageService {

  List<NotificationMessage> findNotificationMessageForCreateByUser(User user, List<NotificationType> notificationTypes);

  List<NotificationMessageDto> findNotificationMessagesForUser(Long userId);

  List<NotificationMessageUser> createNotificationMessagesForUser(User user, List<NotificationMessage> messages);

  void deleteOldMessage();
}
