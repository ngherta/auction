package com.auction.web.dto.request;

import com.auction.web.dto.StatisticEnum;
import lombok.Data;

import java.util.List;

@Data
public class StatisticRequest {
  private List<StatisticEnum> types;
}
