package com.auction.web.dto;

import com.auction.model.enums.WithdrawMoneyStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WithdrawMoneyDto {
  private Long id;
  private UserDto user;
  private Double amount;
  private WithdrawMoneyStatus status;
  private String genDate;
  private String card;
  private String name;
  private String date;
}
