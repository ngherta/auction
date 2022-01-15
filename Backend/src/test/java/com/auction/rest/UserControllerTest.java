package com.auction.rest;

import com.auction.model.User;
import com.auction.service.UserServiceImpl;
import com.auction.service.interfaces.ResetPasswordService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

  @MockBean
  private ResetPasswordService resetPasswordService;
  @MockBean
  private UserServiceImpl userService;

  @Autowired
  private MockMvc mockMvc;

  private User user;

  @BeforeEach
  void setUp() {
    user = User.builder()
            .firstName("asd")
            .email("asd@asd.com")
            .lastName("asd")
            .build();
  }

  @Test
  @WithMockUser(username = "test", roles = "ADMIN")
  void getUsers_whenInvoked_return200() throws Exception {
    mockMvc.perform(get("/api/users")).andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "test", roles = "ADMIN")
  void deleteUser_whenInvoked_returns200() throws Exception {
    user.setId(1000L);
    mockMvc.perform(MockMvcRequestBuilders.delete("/api/users/{id}", user.getId()))
            .andExpect(status().isOk());
    verify(userService, times(1)).deleteById(any(Long.class));
  }

  @Test
  @WithMockUser(username = "test", roles = "ADMIN")
  void getUser_whenInvoked_returns200() throws Exception {
    user.setId(1000L);
    mockMvc.perform(get("/api/users/{id}", user.getId()))
            .andExpect(status().isOk());
  }

  @Test
  @WithMockUser(username = "test", roles = "ADMIN")
  void enableUser_whenInvoked_returns200() throws Exception {
    when(userService.enable(any(Long.class))).thenReturn(new UserDto());
    user.setId(1000L);
    mockMvc.perform(post("/api/users/enable/{id}", user.getId()))
            .andExpect(status().isOk());
  }
}
