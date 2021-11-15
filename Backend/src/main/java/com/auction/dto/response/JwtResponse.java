package com.auction.dto.response;

import com.auction.dto.UserDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private UserDto userDto;

  public JwtResponse(String accessToken, UserDto userDto) {
    this.token = accessToken;
    this.userDto = userDto;
  }
}
