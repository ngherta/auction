package com.auction.service;

import com.auction.config.UserDetailsImpl;
import com.auction.config.jwt.JwtUtils;
import com.auction.dto.UserDto;
import com.auction.dto.request.LoginRequest;
import com.auction.dto.request.SignupRequest;
import com.auction.dto.response.JwtResponse;
import com.auction.dto.response.MessageResponse;
import com.auction.exception.SameCredentialsException;
import com.auction.repository.UserRepository;
import com.auction.repository.UserRoleRepository;
import com.auction.service.interfaces.AuthenticationService;
import com.auction.web.model.Role;
import com.auction.web.model.User;
import com.auction.web.model.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
  @Autowired
  private AuthenticationManager authenticationManager;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserRoleRepository roleRepository;
  @Autowired
  private PasswordEncoder encoder;
  @Autowired
  private JwtUtils jwtUtils;


  @Override
  public JwtResponse authenticateUser(LoginRequest loginRequest) {
    Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = jwtUtils.generateJwtToken(authentication);

    UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

    Optional<User> user = userRepository.findByEmail(loginRequest.getEmail());
    UserDto userDto = UserDto.from(user.get());
    return new JwtResponse(jwt, userDto);
  }

  @Override
  public void register(SignupRequest signUpRequest) throws SameCredentialsException {
    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      throw new SameCredentialsException("Error: Email is already in use!");
    }

    // Create new user's account
    User user = new User(signUpRequest.getEmail(),
                         encoder.encode(signUpRequest.getPassword()));

    Set<Role> roles = new HashSet<>(1);
    Optional<Role> role = roleRepository.findByUserRole(UserRole.USER);
    roles.add(role.get());
    user.setUserRoles(roles);
    user.setFirstName(signUpRequest.getFirstName());
    user.setLastName(signUpRequest.getLastName());
    userRepository.save(user);
  }
}
