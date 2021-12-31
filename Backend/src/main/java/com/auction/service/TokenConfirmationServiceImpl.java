package com.auction.service;

import com.auction.exception.TokenConfirmationNotFoundException;
import com.auction.exception.UserAlreadyEnabledException;
import com.auction.model.TokenConfirmation;
import com.auction.model.User;
import com.auction.repository.TokenConfirmationRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.MailService;
import com.auction.service.interfaces.NotificationService;
import com.auction.service.interfaces.TokenConfirmationService;
import com.auction.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
class TokenConfirmationServiceImpl implements TokenConfirmationService {
  private final UserRepository userRepository;
  private final TokenConfirmationRepository tokenConfirmationRepository;
  private final MailService mailService;
  private final UserService userService;
  private final NotificationService notificationService;

  @Override
  @Transactional
  public void confirm(String confirmation) {
    TokenConfirmation tokenConfirmation = tokenConfirmationRepository.findByConfirmation(confirmation)
            .orElseThrow(() -> new TokenConfirmationNotFoundException(String.format("TokenConfirmation[%s] not found", confirmation)));

    User user = tokenConfirmation.getUser();

    if (user.isEnabled()) {
      throw new UserAlreadyEnabledException("User" + user.getId() + "already enabled!");
    }
    else {
      user.setEnabled(true);
      user = userRepository.save(user);
      notificationService.getNotificationTypeByUser(user);
      tokenConfirmationRepository.delete(tokenConfirmation);
    }
  }

  @Override
  @Transactional
  public void generate(User user) {
    TokenConfirmation confirmation = TokenConfirmation.builder()
            .confirmation(RandomString.make(64))
            .user(user)
            .genDate(LocalDateTime.now())
            .build();

    confirmation = tokenConfirmationRepository.save(confirmation);
    mailService.sendEmailConfirmation(confirmation);
  }

  @Override
  @Transactional
  public void deleteUnconfirmedUsers() {
    List<TokenConfirmation> tokenConfirmations = tokenConfirmationRepository.findAllByGenDateLessThan(LocalDateTime.now().minusHours(1));

    tokenConfirmations.forEach(e -> {
      tokenConfirmationRepository.deleteAllByUser(e.getUser());
      userService.delete(e.getUser());
    });
  }
}
