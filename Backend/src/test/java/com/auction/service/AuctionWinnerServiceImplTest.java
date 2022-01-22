package com.auction.service;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionWinner;
import com.auction.model.User;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionWinnerRepository;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.AuctionWinnerDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuctionWinnerServiceImplTest {

  @Mock
  private AuctionWinnerRepository auctionWinnerRepository;
  @Mock
  private UserService userService;
  @Mock
  private Mapper<AuctionWinner, AuctionWinnerDto> auctionWinnerDtoMapper;

  private AuctionWinnerServiceImpl auctionWinnerService;

  private User user;
  private List<AuctionWinner> auctionWinnerList = new ArrayList<>();
  private List<AuctionWinnerDto> dtos = new ArrayList<>();

  @BeforeEach
  public void setUp() {
    auctionWinnerService = new AuctionWinnerServiceImpl(auctionWinnerRepository, userService, auctionWinnerDtoMapper);
    user = User.builder()
            .lastName("Gherta")
            .email("asd@asd.com")
            .firstName("asd")
            .build();
    AuctionEvent auctionEvent = AuctionEvent.builder()
            .title("asd")
            .finishPrice(123D)
            .build();
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
  void getAllAuctionWinnerForUser_whenInvoked_returnNotEmptyList() {
    when(auctionWinnerRepository.findByUser(any(User.class))).thenReturn(auctionWinnerList);
    when(auctionWinnerDtoMapper.mapList(any(List.class))).thenReturn(dtos);

    assertThat(auctionWinnerService.getAllAuctionWinnerForUser(user)).isNotEmpty();
  }
}
