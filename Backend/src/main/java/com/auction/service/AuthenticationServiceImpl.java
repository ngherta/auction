package com.auction.service;

import com.auction.config.jwt.JwtUtils;
import com.auction.exception.UserNotFoundException;
import com.auction.exception.UserRoleNotFoundException;
import com.auction.model.Role;
import com.auction.model.User;
import com.auction.model.enums.UserRole;
import com.auction.model.mapper.Mapper;
import com.auction.repository.UserRepository;
import com.auction.repository.UserRoleRepository;
import com.auction.service.interfaces.AuthenticationService;
import com.auction.service.interfaces.NotificationGenerationService;
import com.auction.service.interfaces.NotificationService;
import com.auction.service.interfaces.TokenConfirmationService;
import com.auction.service.interfaces.UserService;
import com.auction.validator.UserValidator;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.request.LoginRequest;
import com.auction.web.dto.request.SignupRequest;
import com.auction.web.dto.response.JwtResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final UserService userService;
  private final JwtUtils jwtUtils;
  private final TokenConfirmationService tokenConfirmationService;
  private final NotificationService notificationService;
  private final Mapper<User, UserDto> userToDtoMapper;
  private final List<UserValidator> validators;


  @Override
  @Transactional
  public JwtResponse authenticateUser(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    User user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new UserNotFoundException("User with email[" + loginRequest.getEmail() + "] doesn't exist!"));
    UserDto userDto = userToDtoMapper.map(user);

    return new JwtResponse(jwt, userDto);
  }

  @Override
  @Transactional
  public void register(SignupRequest signUpRequest) throws MessagingException, UnsupportedEncodingException {
    validators.forEach(validator -> validator.validate(signUpRequest));

    User user = userService.create(signUpRequest);

    log.info("New user register {}", user);

    notificationService.createDefaultNotificationsForUser(user);
    tokenConfirmationService.generate(user);
  }

  @Override
  public JwtResponse refreshToken(String token) {
    String email = jwtUtils.getUserNameFromJwtToken(token);
//    String email = "asd@asd.com";

    User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new UsernameNotFoundException("User with email" + email + "doesn't exist!"));
    String refreshToken = jwtUtils.refreshToken(user);
    UserDto userDto = userToDtoMapper.map(user);

    log.info("Token for user[{}] refreshed", user.getId());
    return new JwtResponse(refreshToken, userDto);
  }


}
