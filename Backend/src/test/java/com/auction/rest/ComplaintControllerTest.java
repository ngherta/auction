package com.auction.rest;

import com.auction.model.enums.ComplaintStatus;
import com.auction.service.interfaces.ComplaintService;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.ComplaintAuditDto;
import com.auction.web.dto.ComplaintDto;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.request.ComplaintAdminRequest;
import com.auction.web.dto.request.ComplaintRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ComplaintControllerTest {
  @MockBean
  private ComplaintService complaintService;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  private ComplaintAdminRequest adminRequest = new ComplaintAdminRequest();
  private ComplaintRequest complaintRequest = new ComplaintRequest();
  private ComplaintDto complaintDto;

  @BeforeEach
  void setUp() {
    adminRequest.setAdminId(1L);
    adminRequest.setComplaintId(2L);
    adminRequest.setStatus(ComplaintStatus.WAITING);

    complaintRequest.setMessage("block!");
    complaintRequest.setAuctionEventId(4L);
    complaintRequest.setUserId(6L);

    complaintDto = ComplaintDto.builder()
            .id(100L)
            .auctionEvent(new AuctionEventDto())
            .genDate(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yy HH:mm")))
            .status(adminRequest.getStatus())
            .user(new UserDto())
            .message("test")
            .build();
  }

  @Test
  @WithMockUser(username = "test", roles = "ADMIN")
  void getAll_whenInvoked_return200() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.get("/api/complaint"))
            .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "test", roles = "ADMIN")
  void satisfyComplaint_whenInvoked_return200() throws Exception {
    when(complaintService.satisfyComplaint(any(ComplaintAdminRequest.class)))
            .thenReturn(new ComplaintAuditDto());

    mockMvc.perform(MockMvcRequestBuilders.post("/api/complaint/answer")
                            .content(objectMapper.writeValueAsString(adminRequest)))
            .andExpect(status().isOk());
  }


  @Test
  @WithMockUser(username = "test", roles = "ADMIN")
  void create_whenInvoked_return200() throws Exception {
    mockMvc.perform(MockMvcRequestBuilders.post("/api/complaint")
                            .content(objectMapper.writeValueAsString(complaintRequest)))
            .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "test", roles = "ADMIN")
  void getAll_whenInvoked_returnListOfComplaints() throws Exception {
    when(complaintService.getAll(1, 2)).thenReturn(Page.empty());

    mockMvc.perform(MockMvcRequestBuilders.get("/api/complaint"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$[0].id").value(complaintDto.getId()))
            .andExpect(jsonPath("$[0].message").value(complaintDto.getMessage()))
            .andExpect(jsonPath("$[0].genDate").isString())
            .andExpect(jsonPath("$[0].status").value(complaintDto.getStatus().name()));
  }
}
