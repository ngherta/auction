package com.auction.service;

import com.auction.model.NotificationMessage;
import com.auction.model.NotificationMessageUser;
import com.auction.model.User;
import com.auction.model.enums.NotificationType;
import com.auction.model.fixture.NotificationMessageFixture;
import com.auction.model.fixture.UserFixture;
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
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    user = UserFixture.user();

    NotificationMessageUser messageUser = NotificationMessageFixture.notificationMessageUser();
    notificationMessageList.add(messageUser);

    NotificationMessageDto dto = NotificationMessageDto.builder()
            .seen(messageUser.getSeen())
            .genDate(messageUser.getNotificationMessage().getGenDate().toString())
            .type(messageUser.getNotificationMessage().getType())
            .message(messageUser.getNotificationMessage().getMessage())
            .build();
    dtos.add(dto);

    notificationMessages.add(new NotificationMessage("test", NotificationType.NEW_MESSAGE, LocalDateTime.now(), false));
  }

  @Test
  void createNotificationMessagesForUser_whenInvoked_repositoryInvokedOneTime() {
    notificationMessageService.createNotificationMessagesForUser(user, notificationMessages);
    verify(notificationMessageUserRepository, times(1)).saveAll(any(List.class));
  }

  @Test
  void seen_whenInvoked_findAllByUserAndAndNotificationMessageInInvokedOneTime() {
    when(userRepository.findById(any())).thenReturn(Optional.of(user));
    when(notificationMessageRepository.findAllByIdIn(any(List.class))).thenReturn(notificationMessageList);
    when(notificationMessageUserRepository
                 .findAllByUserAndAndNotificationMessageIn(any(), any()))
            .thenReturn(notificationMessageList);

    notificationMessageService.seen(user.getId(), List.of(1L));
    verify(notificationMessageUserRepository, times(1)).findAllByUserAndAndNotificationMessageIn(any(), any());
  }
}
