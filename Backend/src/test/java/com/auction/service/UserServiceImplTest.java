package com.auction.service;

import com.auction.model.Role;
import com.auction.model.User;
import com.auction.model.enums.UserRole;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;

import java.time.LocalDate;

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

  private Role role;
  private User user;

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

    role = Role.builder().userRole(UserRole.USER).build();

    user = User.builder()
            .email("asdasda")
            .firstName("asdasda")
            .build();
    user.setId(5L);
  }

  @Test
  void createNewUser_returnsNewUser() {
    when(roleRepository.findByUserRole(any(UserRole.class))).thenReturn(java.util.Optional.ofNullable(role));
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
  void test() {
    int x = 4;
    assertThat(x).isEqualTo(4);
  }
}
