package com.auction.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class ChatMessageDto {
  private String message;
  private Long chatRoom;
  private Long senderId;
  private String senderFirstName;
  private String senderLastName;
  private String genDate;
}
