package com.auction.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteUserRequest {
  private Long userId;

  @JsonCreator
  public DeleteUserRequest(@JsonProperty Long userId) {
    this.userId = userId;
  }
}
