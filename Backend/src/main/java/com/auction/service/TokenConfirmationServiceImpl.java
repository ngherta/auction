package com.auction.service;

import com.auction.exception.TokenConfirmationNotFoundException;
import com.auction.exception.UserAlreadyEnabledException;
import com.auction.repository.TokenConfirmationRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.MailService;
import com.auction.service.interfaces.TokenConfirmationService;
import com.auction.model.TokenConfirmation;
import com.auction.model.User;
import lombok.AllArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class TokenConfirmationServiceImpl implements TokenConfirmationService {
  private final UserRepository userRepository;
  private final TokenConfirmationRepository tokenConfirmationRepository;
  private final MailService mailService;

  @Override
  public void confirm(String confirmation) throws TokenConfirmationNotFoundException, UserAlreadyEnabledException {
    Optional<TokenConfirmation> tokenConfirmation = tokenConfirmationRepository.findByConfirmation(confirmation);
    if (tokenConfirmation.isEmpty()) {
      throw new TokenConfirmationNotFoundException(String.format("TokenConfirmation[%s] not found", confirmation));
    }

    User user = tokenConfirmation.get().getUser();

    if (!tokenConfirmation.get().getUser().isEnabled()) {
      throw new UserAlreadyEnabledException("User" + user.getId() + "already enabled");
    }
    else {
      user.setEnabled(true);
      userRepository.save(user);
      tokenConfirmationRepository.delete(tokenConfirmation.get());
    }
  }

  @Override
  @Transactional
  public void generate(User user) throws MessagingException, UnsupportedEncodingException {
    String randomCode = RandomString.make(64);
    TokenConfirmation confirmation = new TokenConfirmation();
    confirmation.setConfirmation(randomCode);
    confirmation.setUser(user);
    confirmation = tokenConfirmationRepository.save(confirmation);
    mailService.sendConfirmation(confirmation);
  }
}
