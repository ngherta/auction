package com.auction.web.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ChatMessageRequest {
  @NotNull
  private Long senderId;
  @NotNull
  private String message;
}
