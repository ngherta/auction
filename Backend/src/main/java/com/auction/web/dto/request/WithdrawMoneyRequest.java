package com.auction.web.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class WithdrawMoneyRequest {
  @NotNull
  private Long userId;
  @NotNull
  private Double amount;
  @Size(min = 16, max = 16)
  private String card;
  @NotBlank
  private String date;
  @NotBlank
  private String name;
}
