package com.auction.web.dto.request;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DeleteUserRequest {
  @NotNull
  private Long userId;
}
