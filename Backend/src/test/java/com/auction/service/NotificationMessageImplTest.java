package com.auction.service;

import com.auction.model.NotificationMessage;
import com.auction.model.NotificationMessageUser;
import com.auction.model.User;
import com.auction.model.enums.NotificationType;
import com.auction.model.mapper.Mapper;
import com.auction.repository.NotificationMessageRepository;
import com.auction.repository.NotificationMessageUserRepository;
import com.auction.repository.UserRepository;
import com.auction.web.dto.NotificationMessageDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificationMessageImplTest {

  @Mock
  private UserRepository userRepository;
  @Mock
  private NotificationMessageRepository notificationMessageRepository;
  @Mock
  private NotificationMessageUserRepository notificationMessageUserRepository;
  @Mock
  private Mapper<NotificationMessageUser, NotificationMessageDto> mapper;

  private NotificationMessageServiceImpl notificationMessageService;
  private List<NotificationMessageDto> dtos = new ArrayList<>();
  private List<NotificationMessageUser> notificationMessageList = new ArrayList<>();
  private List<NotificationMessage> notificationMessages = new ArrayList<>();
  private User user;

  @BeforeEach
  void setUp() {
    notificationMessageService = new NotificationMessageServiceImpl(userRepository,
                                                                    notificationMessageRepository,
                                                                    notificationMessageUserRepository,
                                                                    mapper);

    user = User.builder()
            .firstName("asd")
            .email("asd@asd.com")
            .email("asd")
            .build();

    NotificationMessageUser messageUser = NotificationMessageUser.builder()
            .seen(false)
            .user(user)
            .notificationMessage(new NotificationMessage("test", NotificationType.NEW_MESSAGE, LocalDateTime.now()))
            .build();
    notificationMessageList.add(messageUser);

    NotificationMessageDto dto = NotificationMessageDto.builder()
            .seen(messageUser.getSeen())
            .genDate(messageUser.getNotificationMessage().getGenDate().toString())
            .type(messageUser.getNotificationMessage().getType())
            .message(messageUser.getNotificationMessage().getMessage())
            .build();
    dtos.add(dto);

    notificationMessages.add(new NotificationMessage("test", NotificationType.NEW_MESSAGE, LocalDateTime.now()));
  }

//  @Test
//  void findNotificationMessagesForUser_whenInvoked_returnListONotificationMessageDto() {
//    when(userRepository.findById(any(Long.class))).thenReturn(java.util.Optional.ofNullable(user));
//    when(notificationMessageUserRepository.findByUser(any(User.class))).thenReturn(notificationMessageList);
//    when(mapper.mapList(any(List.class))).thenReturn(dtos);
//
//    assertThat(notificationMessageService.findNotificationMessagesForUser(100L))
//            .isNotEqualTo(Collections.EMPTY_LIST);
//  }

  @Test
  void createNotificationMessagesForUser_whenInvoked_repositoryInvokedOneTIme() {
    notificationMessageService.createNotificationMessagesForUser(user, notificationMessages);
    verify(notificationMessageUserRepository, times(1)).saveAll(any(List.class));
  }
}
