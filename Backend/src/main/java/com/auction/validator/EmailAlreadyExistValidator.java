package com.auction.validator;

import com.auction.exception.SameCredentialsException;
import com.auction.repository.UserRepository;
import com.auction.web.dto.request.SignupRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailAlreadyExistValidator implements UserValidator{

  private final UserRepository userRepository;

  @Override
  public void validate(SignupRequest request) {
    if (Boolean.TRUE.equals(userRepository.existsByEmail(request.getEmail()))) {
      throw new SameCredentialsException("Error: Email is already in use!");
    }
  }
}
