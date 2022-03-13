package com.auction.service;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.User;
import com.auction.model.fixture.AuctionEventFixture;
import com.auction.model.fixture.UserFixture;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionWinnerRepository;
import com.auction.repository.PaymentAuditRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.PaymentService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.AuctionWinnerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuctionWinnerServiceImplTest {

  @Mock
  private AuctionWinnerRepository auctionWinnerRepository;
  @Mock
  private UserService userService;
  @Mock
  private AuctionEventService auctionEventService;
  @Mock
  private Mapper<AuctionWinner, AuctionWinnerDto> auctionWinnerDtoMapper;
  @Mock
  private PaymentService paymentService;
  @Mock
  private ApplicationEventPublisher publisher;
  @Mock
  private PaymentAuditRepository paymentAuditRepository;


  private AuctionWinnerServiceImpl auctionWinnerService;

  private User user;
  private List<AuctionWinner> auctionWinnerList = new ArrayList<>();
  private List<AuctionWinnerDto> dtos = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    auctionWinnerService = new AuctionWinnerServiceImpl(auctionWinnerRepository,
                                                        userService,
                                                        auctionWinnerDtoMapper,
                                                        paymentService,
                                                        publisher,
                                                        paymentAuditRepository);
    user = UserFixture.user();
    AuctionEvent auctionEvent = AuctionEventFixture.auctionEvent();
    AuctionWinner auctionWinner = AuctionWinner.builder()
            .auctionEvent(auctionEvent)
            .price(123D)
            .genDate(LocalDateTime.now())
            .user(user)
            .build();

    AuctionWinnerDto dto = AuctionWinnerDto.builder()
            .price(auctionWinner.getPrice())
            .auctionEvent(new AuctionEventDto())
            .genDate(auctionWinner.getGenDate().toString())
            .build();

    dtos.add(dto);

    auctionWinnerList.add(auctionWinner);
  }

  @Test
  void getAllAuctionWinnerForUser_whenInvoked_returnEmptyList() {
    when(auctionWinnerRepository.findByUser(any(User.class), any())).thenReturn(Page.empty());
    when(auctionWinnerDtoMapper.mapList(any(List.class))).thenReturn(dtos);

    assertThat(auctionWinnerService.getAllAuctionWinnerForUser(user,1, 5)).isEmpty();
  }
}
