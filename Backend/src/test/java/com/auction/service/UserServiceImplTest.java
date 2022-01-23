package com.auction.service;

import com.auction.model.Role;
import com.auction.model.User;
import com.auction.model.enums.UserRole;
import com.auction.model.fixture.UserFixture;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionActionRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.UserRepository;
import com.auction.repository.UserRoleRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.NotificationService;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.request.SignupRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
  @Mock
  private UserRepository userRepository;
  @Mock
  private AuctionEventRepository auctionEventRepository;
  @Mock
  private AuctionActionRepository auctionActionRepository;
  @Mock
  private NotificationService notificationService;
  @Mock
  private AuctionEventService auctionEventService;
  @Mock
  private Mapper<User, UserDto> userToDtoMapper;
  @Mock
  private UserRoleRepository roleRepository;
  @Mock
  private PasswordEncoder encoder;

  private UserServiceImpl userService;

  private Set<Role> roles = new HashSet<>();
  private User user;
  private UserDto dto = new UserDto();

  @BeforeEach
  public void setUp() {
    //SUT
    userService = new UserServiceImpl(userRepository,
                                      auctionEventRepository,
                                      auctionActionRepository,
                                      notificationService,
                                      userToDtoMapper,
                                      roleRepository,
                                      encoder);
    userService.setAuctionEventService(auctionEventService);
    roles = UserFixture.userRole();
    user = UserFixture.user();

    dto.setEnabled(false);
  }

  @Test
  void create_whenInvoked_returnNewUser() {
    when(roleRepository.findByUserRole(any(UserRole.class))).thenReturn(roles.stream().findFirst());
    when(userRepository.save(any(User.class))).thenReturn(user);

    SignupRequest request = new SignupRequest();
    request.setFirstName("Nicolae");
    request.setLastName("Gherta");
    request.setEmail("asdasd@asdad.com");
    request.setPassword("qwerty123");
    request.setBirthday(LocalDate.now().minusDays(2));

    user = userService.create(request);

    //Check that user was saved
    assertThat(user).isNotNull();
  }

  @Test
  void disable_whenInvoked_returnDisabledUser() {
    when(userRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(user));
    user = UserFixture.disabledUser();
    when(userRepository.save(any(User.class))).thenReturn(UserFixture.disabledUser());
    when(userToDtoMapper.map(any(User.class))).thenReturn(dto);

    assertThat(userService.disable(user.getId()).getEnabled()).isFalse();
  }

  @Test
  void enable_whenInvoked_returnEnabledUser() {
    when(userRepository.findById(any())).thenReturn(java.util.Optional.ofNullable(user));
    user = UserFixture.disabledUser();
    when(userRepository.save(any(User.class)))
            .thenReturn(UserFixture.user());
    when(userToDtoMapper.map(any(User.class)))
            .thenReturn(dto);

    assertThat(userService.disable(user.getId()).getEnabled()).isFalse();
  }
}
