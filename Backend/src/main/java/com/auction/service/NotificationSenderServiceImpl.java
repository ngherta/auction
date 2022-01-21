package com.auction.service;

import com.auction.model.NotificationMessageUser;
import com.auction.model.mapper.Mapper;
import com.auction.repository.NotificationRepository;
import com.auction.service.interfaces.NotificationSenderService;
import com.auction.web.dto.NotificationMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class NotificationSenderServiceImpl implements NotificationSenderService {

  private final NotificationRepository notificationRepository;
  private final SimpMessagingTemplate messagingTemplate;
  private final Mapper<NotificationMessageUser, NotificationMessageDto> notificationMessageDtoMapper;


  @Override
  @Transactional
  public void sendNotificationToUsers(List<NotificationMessageUser> messages) {
    for (NotificationMessageUser message : messages) {
      NotificationMessageDto dto = notificationMessageDtoMapper.map(message);
      messagingTemplate.convertAndSend("/notification/" + message.getUser().getId(), dto);
    }
  }

  @Override
  @Transactional
  public void sendNotificationsToUser(List<NotificationMessageUser> messages) {
    messages.forEach(message -> {
      NotificationMessageDto dto = notificationMessageDtoMapper.map(message);
      messagingTemplate.convertAndSend("/notification/" + message.getUser().getId(), dto);
    });
  }

}
