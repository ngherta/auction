package com.auction.service;

import com.auction.repository.TokenConfirmationRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.TokenConfirmationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TokenConfirmationServiceImpl implements TokenConfirmationService {
  private final UserRepository userRepository;
  private final TokenConfirmationRepository tokenConfirmationRepository;

  @Override
  public void confirm(String confirmation) {

  }
}
