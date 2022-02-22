package com.auction.web.dto.response.statistic;

import lombok.Data;

import java.util.List;

@Data
public class Statistic {
  private List<CategoryCount> categoryCount;
  private List<UserCountPerMonth> userCountPerMonth;
  private List<CategoryCount> subCategoryCount;
  private List<CommissionPerMouth> commissionPerMouths;
}
