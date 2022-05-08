package com.auction.service;

import com.auction.exception.NotificationNotFoundException;
import com.auction.exception.UserNotFoundException;
import com.auction.model.NotificationMessage;
import com.auction.model.NotificationMessageUser;
import com.auction.model.User;
import com.auction.model.enums.NotificationType;
import com.auction.model.mapper.Mapper;
import com.auction.repository.NotificationMessageRepository;
import com.auction.repository.NotificationMessageUserRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.NotificationMessageService;
import com.auction.web.dto.NotificationMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
class NotificationMessageServiceImpl implements NotificationMessageService {

  private final UserRepository userRepository;
  private final NotificationMessageRepository notificationMessageRepository;
  private final NotificationMessageUserRepository notificationMessageUserRepository;
  private final Mapper<NotificationMessageUser, NotificationMessageDto> notificationMessageDtoMapper;

  @Transactional
  @Override
  public List<NotificationMessageUser> createNotificationMessagesForUser(User user, List<NotificationMessage> messages) {
    List<NotificationMessageUser> notificationMessageUsers = new ArrayList<>();

    messages.forEach(e -> {
      NotificationMessageUser notification = NotificationMessageUser.builder()
              .notificationMessage(e)
              .seen(false)
              .user(user)
              .build();
      notificationMessageUsers.add(notification);
    });

    return notificationMessageUserRepository.saveAll(notificationMessageUsers);
  }

  @Override
  @Transactional
  public List<NotificationMessage> findNotificationMessageForCreateByUser(User user, List<NotificationType> notificationTypes) {
    List<String> list = notificationTypes.stream().map(Enum::name).collect(Collectors.toList());
    return notificationMessageRepository.findUncreatedNotificationForUser(user.getId(), list);
  }

  @Override
  @Transactional
  public void deleteOldMessage() {
    LocalDateTime localDateTime = LocalDateTime.now().minusDays(5);
    List<NotificationMessage> messageList = getMessagesOlderThan(localDateTime);

    messageList.forEach(e -> {
      notificationMessageUserRepository.deleteAllByNotificationMessage(e);
      notificationMessageRepository.delete(e);
    });
  }

  @Transactional(readOnly = true)
  @Override
  public List<NotificationMessage> getMessagesOlderThan(LocalDateTime localDateTime) {
    return notificationMessageRepository.findAllOlderThan(localDateTime);
  }

  @Override
  @Transactional(readOnly = true)
  public List<NotificationMessageUser> getNotificationsForUser(User user) {
    return notificationMessageUserRepository.findByUserOrderByGenDate(user.getId());
  }

  @Override
  @Transactional
  public void seen(Long userId, List<Long> notificationMessageId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User[" + userId + "] doesn't exist"));
    List<NotificationMessage> notificationMessages = notificationMessageRepository.findAllByIdIn(notificationMessageId);
    List<NotificationMessageUser> notificationMessageUsers = notificationMessageUserRepository.findAllByUserAndAndNotificationMessageIn(user, notificationMessages);
    for (NotificationMessageUser notificationMessageUser : notificationMessageUsers) {
      notificationMessageUser.setSeen(true);
    }
    notificationMessageUserRepository.saveAll(notificationMessageUsers);
  }

  @Override
  @Transactional(readOnly = true)
  public NotificationMessageUser getNotificationMessageByUserAndMessage(User user, NotificationMessage notificationMessage) {
    return notificationMessageUserRepository.findByUserAndNotificationMessage(user, notificationMessage)
            .orElseThrow(() -> new NotificationNotFoundException("NotificationMessageUser not found for message with id = " + notificationMessage.getId()));
  }

  @Override
  @Transactional
  public void seen(Long userId, Long notificationMessageId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User[" + userId + "] doesn't exist"));
    NotificationMessage notificationMessage = notificationMessageRepository.findById(notificationMessageId)
            .orElseThrow(() -> new NotificationNotFoundException("NotificationMessage[" + notificationMessageId + "] doesn't exist!"));
    NotificationMessageUser notificationMessageUser = getNotificationMessageByUserAndMessage(user, notificationMessage);

    notificationMessageUser.setSeen(true);
  }
}
