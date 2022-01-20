package com.auction.service.interfaces;


import com.auction.web.dto.response.statistic.CategoryCount;
import com.auction.web.dto.response.statistic.Statistic;
import com.auction.web.dto.response.statistic.UserCountPerMonth;

import java.util.List;

public interface StatisticService {
  List<UserCountPerMonth> getCountOfUsersPerMouth();

  List<CategoryCount> getCategoryCount();

  List<CategoryCount> getSubCategoryCount();

  Statistic getStatistic();
}
