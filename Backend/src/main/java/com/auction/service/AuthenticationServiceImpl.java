package com.auction.service;

import com.auction.config.jwt.JwtUtils;
import com.auction.exception.SameCredentialsException;
import com.auction.exception.UserNotFoundException;
import com.auction.exception.UserRoleNotFoundException;
import com.auction.model.Role;
import com.auction.model.User;
import com.auction.model.enums.UserRole;
import com.auction.model.mapper.Mapper;
import com.auction.repository.UserRepository;
import com.auction.repository.UserRoleRepository;
import com.auction.service.interfaces.AuthenticationService;
import com.auction.service.interfaces.TokenConfirmationService;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final UserRoleRepository roleRepository;
  private final PasswordEncoder encoder;
  private final JwtUtils jwtUtils;
  private final TokenConfirmationService tokenConfirmationService;
  private final Mapper<User, UserDto> userToDtoMapper;


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
    if (Boolean.TRUE.equals(userRepository.existsByEmail(signUpRequest.getEmail()))) {
      throw new SameCredentialsException("Error: Email is already in use!");
    }

    // Create new user's account
    User user = new User(signUpRequest.getEmail(),
                         encoder.encode(signUpRequest.getPassword()));

    Set<Role> roles = new HashSet<>(1);
    Role role = roleRepository.findByUserRole(UserRole.USER)
            .orElseThrow(() -> new UserRoleNotFoundException(UserRole.USER.name() + "doesn't exist."));
    roles.add(role);
    user.setUserRoles(roles);
    user.setFirstName(signUpRequest.getFirstName());
    user.setLastName(signUpRequest.getLastName());
    user = userRepository.save(user);

    log.info("New user register {}", user);

    tokenConfirmationService.generate(user);
  }
}
