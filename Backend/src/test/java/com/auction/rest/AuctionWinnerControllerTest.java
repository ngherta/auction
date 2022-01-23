package com.auction.rest;

import com.auction.model.User;
import com.auction.service.AuctionWinnerServiceImpl;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.AuctionWinnerDto;
import com.auction.web.dto.PaymentOrderDto;
import com.auction.web.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuctionWinnerControllerTest {

  @MockBean
  private AuctionWinnerServiceImpl auctionWinnerService;

  @Autowired
  private MockMvc mockMvc;

  List<AuctionWinnerDto> listOfDtos = new ArrayList<>();


  @BeforeEach
  void setUp() {
    AuctionWinnerDto auctionWinnerDto = AuctionWinnerDto.builder()
            .user(new UserDto())
            .auctionEvent(new AuctionEventDto())
            .genDate(LocalDateTime.now().toString())
            .paymentOrder(new PaymentOrderDto())
            .price(100D)
            .build();
    listOfDtos.add(auctionWinnerDto);
  }

  @Test
  @WithMockUser(username = "test", roles = "ADMIN")
  void getUsers_whenInvoked_return200() throws Exception {
    mockMvc.perform(get("/api/winner/")).andExpect(status().isOk());
  }
}
