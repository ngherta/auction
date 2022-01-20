package com.auction.rest;

import com.auction.model.enums.AuctionType;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.web.dto.AuctionEventDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AuctionEventControllerTest {
  @MockBean
  private AuctionEventService auctionEventService;

  @Autowired
  private MockMvc mockMvc;

  private AuctionEventDto auctionEventDto;

  @BeforeEach
  void setUp() {
    auctionEventDto = AuctionEventDto.builder()
            .id(100L)
            .title("title")
            .auctionType(AuctionType.CHARITY)
            .description("description")
            .startDate(LocalDateTime.now().toString())
            .finishDate(LocalDateTime.now().plusDays(1).toString())
            .startPrice(10D)
            .finishPrice(15D)
            .build();
  }

  @Test
  @WithMockUser(username = "test", roles = "ADMIN")
  void getAuctions_whenInvoked_return200() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/auction")
                            .param("page", "1")
                            .param("perPage", "5"))
            .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "test", roles = "ADMIN")
  void getSortAuctions_whenInvoked_return200() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/auction/sort")
                            .param("page", "1")
                            .param("perPage", "5"))
            .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "test", roles = "ADMIN")
  void getAuction_whenInvoked_returns200() throws Exception {
    when(auctionEventService.getById(any(Long.class)))
            .thenReturn(auctionEventDto);

    mockMvc.perform(get("/api/auction/{id}", auctionEventDto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("title").value(auctionEventDto.getTitle()))
            .andExpect(jsonPath("description").value(auctionEventDto.getDescription()))
            .andExpect(jsonPath("startDate").value(auctionEventDto.getStartDate()))
            .andExpect(jsonPath("finishDate").value(auctionEventDto.getFinishDate()))
            .andExpect(jsonPath("startPrice").value(auctionEventDto.getStartPrice()))
            .andExpect(jsonPath("finishPrice").value(auctionEventDto.getFinishPrice()));
  }

  @Test
  @WithMockUser(username = "test", roles = "ADMIN")
  void deleteAuction_whenInvoked_returns200() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/auction/{id}", auctionEventDto.getId()))
            .andExpect(status().isOk());
    verify(auctionEventService, times(1)).deleteById(any(Long.class));
  }

  @Test
  @WithMockUser(username = "test", roles = "ADMIN")
  void blockAuction_whenInvoked_returns200() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.put("/api/auction/block/{id}", auctionEventDto.getId()))
            .andExpect(status().isOk());
    verify(auctionEventService, times(1)).blockAuctionEventById(any(Long.class));
  }
}
