package com.auction.web.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class ComplaintRequest {

  @NotNull
  private Long auctionEventId;
  @NotNull
  private Long userId;
  @NotBlank
  private String message;

}
