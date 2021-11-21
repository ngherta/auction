package com.auction.service;

import com.auction.config.UserDetailsImpl;
import com.auction.config.jwt.JwtUtils;
import com.auction.exception.UserNotFoundException;
import com.auction.exception.UserRoleNotFoundException;
import com.auction.model.mapper.UserToDtoMapper;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.request.LoginRequest;
import com.auction.web.dto.request.SignupRequest;
import com.auction.web.dto.response.JwtResponse;
import com.auction.exception.SameCredentialsException;
import com.auction.repository.UserRepository;
import com.auction.repository.UserRoleRepository;
import com.auction.service.interfaces.AuthenticationService;
import com.auction.model.Role;
import com.auction.model.User;
import com.auction.model.enums.UserRole;
import lombok.AllArgsConstructor;
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
public class AuthenticationServiceImpl implements AuthenticationService {

  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final UserRoleRepository roleRepository;
  private final PasswordEncoder encoder;
  private final JwtUtils jwtUtils;
  private final TokenConfirmationServiceImpl tokenConfirmationService;
  private final UserToDtoMapper userToDtoMapper;


  @Override
  @Transactional
  public JwtResponse authenticateUser(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    User user = userRepository.findByEmail(loginRequest.getEmail())
            .orElseThrow(() -> new UserNotFoundException("User with email[" + loginRequest.getEmail() + "] doesn't exist!"));
    UserDto userDto = userToDtoMapper.map(user);
    return new JwtResponse(jwt, userDto);
  }

  @Override
  @Transactional
  public void register(SignupRequest signUpRequest) throws MessagingException, UnsupportedEncodingException {
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
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

    tokenConfirmationService.generate(user);
  }
}
