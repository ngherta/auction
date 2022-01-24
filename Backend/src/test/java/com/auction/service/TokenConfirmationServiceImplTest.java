package com.auction.service;

import com.auction.model.TokenConfirmation;
import com.auction.model.User;
import com.auction.model.fixture.UserFixture;
import com.auction.repository.TokenConfirmationRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.MailService;
import com.auction.service.interfaces.NotificationService;
import com.auction.service.interfaces.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TokenConfirmationServiceImplTest {
  @Mock
  private UserRepository userRepository;
  @Mock
  private TokenConfirmationRepository tokenConfirmationRepository;
  @Mock
  private MailService mailService;
  @Mock
  private UserService userService;
  @Mock
  private NotificationService notificationService;

  private TokenConfirmationServiceImpl tokenConfirmationService;
  private TokenConfirmation tokenConfirmation;
  private User user;

  @BeforeEach
  public void setUp() {
    user = UserFixture.user();
    tokenConfirmationService = new TokenConfirmationServiceImpl(userRepository,
                                                                tokenConfirmationRepository,
                                                                mailService,
                                                                userService,
                                                                notificationService);

    tokenConfirmation = TokenConfirmation.builder()
            .genDate(LocalDateTime.now())
            .confirmation("asd")
            .user(user)
            .build();
  }

  @Test
  void confirm_whenInvoked_callFindByConfirmationOnce() {
    user.setEnabled(false);
    when(tokenConfirmationRepository.findByConfirmation(any(String.class))).thenReturn(Optional.of(tokenConfirmation));
    when(userRepository.save(any(User.class))).thenReturn(user);

    tokenConfirmationService.confirm("");
    verify(tokenConfirmationRepository, times(1))
            .findByConfirmation(any(String.class));
  }

  @Test
  void confirm_whenUserIsEnabled_throwUserAlreadyEnabledException() {
    user.setEnabled(true);
    when(tokenConfirmationRepository.findByConfirmation(any(String.class))).thenReturn(Optional.of(tokenConfirmation));

    Exception exception = assertThrows(RuntimeException.class, () -> {
      tokenConfirmationService.confirm("");
    });

    String expectedMessage = "already enabled!";
    String actualMessage = exception.getMessage();

    assertThat(actualMessage.contains(expectedMessage)).isTrue();
  }

  @Test
  void generate_whenInvoked_callSaveOnce() {
    user.setEnabled(false);
    when(tokenConfirmationRepository.save(any(TokenConfirmation.class))).thenReturn(tokenConfirmation);

    tokenConfirmationService.generate(user);
    verify(tokenConfirmationRepository, times(1))
            .save(any(TokenConfirmation.class));
  }
}
