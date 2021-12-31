package com.auction.service;

import com.auction.model.User;
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

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
//  @Mock
//  private UserRepository userRepository;
//  @Mock
//  private AuctionEventRepository auctionEventRepository;
//  @Mock
//  private AuctionActionRepository auctionActionRepository;
//  @Mock
//  private NotificationService notificationService;
//  @Mock
//  private AuctionEventService auctionEventService;
//  @Mock
//  private Mapper<User, UserDto> userToDtoMapper;
//  @Mock
//  private UserRoleRepository roleRepository;
//  @Mock
//  private PasswordEncoder encoder;
//
//  private UserServiceImpl userService;
//
//  @BeforeEach
//  public void setUp() {
//    //SUT
//    userService = new UserServiceImpl(userRepository,
//                                      auctionEventRepository,
//                                      auctionActionRepository,
//                                      notificationService,
//                                      userToDtoMapper,
//                                      roleRepository,
//                                      encoder);
//    userService.setAuctionEventService(auctionEventService);
//  }
//
//  @Test
//  void createNewUser_returnsNewUser() {
////    userService = new UserServiceImpl(userRepository,
////                                      auctionEventRepository,
////                                      auctionActionRepository,
////                                      notificationService,
////                                      userToDtoMapper,
////                                      roleRepository,
////                                      encoder);
////    userService.setAuctionEventService(auctionEventService);
//    SignupRequest request = new SignupRequest();
//    request.setFirstName("Nicolae");
//    request.setLastName("Gherta");
//    request.setEmail("asdasd@asdad.com");
//    request.setPassword("qwerty123");
//    request.setBirthday(LocalDate.now().minusDays(2));
//
//    final User user = userService.create(request);
//
//    assertThat(user).isNotNull();
//    assertThat(user.getId()).isNotNull();
//  }

  @Test
  void test() {
    int x = 4;
    assertThat(x).isEqualTo(4);
  }
}
