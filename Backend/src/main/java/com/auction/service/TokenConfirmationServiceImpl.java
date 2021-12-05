package com.auction.service;

import com.auction.exception.TokenConfirmationNotFoundException;
import com.auction.exception.UserAlreadyEnabledException;
import com.auction.model.TokenConfirmation;
import com.auction.model.User;
import com.auction.repository.TokenConfirmationRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.MailService;
import com.auction.service.interfaces.TokenConfirmationService;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
class TokenConfirmationServiceImpl implements TokenConfirmationService {
  private final UserRepository userRepository;
  private final TokenConfirmationRepository tokenConfirmationRepository;
  private final MailService mailService;

  @Override
  @Transactional
  public void confirm(String confirmation) {
    TokenConfirmation tokenConfirmation = tokenConfirmationRepository.findByConfirmation(confirmation)
            .orElseThrow(() -> new TokenConfirmationNotFoundException(String.format("TokenConfirmation[%s] not found", confirmation)));

    User user = tokenConfirmation.getUser();

    if (tokenConfirmation.getUser().isEnabled()) {
      throw new UserAlreadyEnabledException("User" + user.getId() + "already enabled!");
    }
    else {
      user.setEnabled(true);
      userRepository.save(user);
      tokenConfirmationRepository.delete(tokenConfirmation);
    }
  }

  @Override
  @Transactional
  public void generate(User user) {
    TokenConfirmation confirmation = TokenConfirmation.builder()
            .confirmation(RandomString.make(64))
            .user(user)
            .build();

    confirmation = tokenConfirmationRepository.save(confirmation);
    mailService.sendEmailConfirmation(confirmation);
  }
}
