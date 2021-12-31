package com.auction.web.dto.response.statistic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserCountPerMonth {
  private Integer index;
  private String date;
  private String month;
  private Long count;
}
