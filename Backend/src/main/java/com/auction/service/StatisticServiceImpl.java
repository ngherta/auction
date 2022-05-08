package com.auction.service;

import com.auction.projection.CategoryCountProjection;
import com.auction.projection.CommissionPerMouthProjection;
import com.auction.projection.UserCountPerMonthProjection;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.PaymentAuditRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.StatisticService;
import com.auction.web.dto.StatisticEnum;
import com.auction.web.dto.response.statistic.CategoryCount;
import com.auction.web.dto.response.statistic.CommissionPerMouth;
import com.auction.web.dto.response.statistic.Statistic;
import com.auction.web.dto.response.statistic.UserCountPerMonth;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
class StatisticServiceImpl implements StatisticService {
  private final UserRepository userRepository;
  private final AuctionEventRepository auctionEventRepository;
  private final PaymentAuditRepository paymentAuditRepository;

  @Transactional(readOnly = true)
  @Override
  public Statistic getStatistic() {
    Statistic statistic = new Statistic();
    statistic.setCategoryCount(getCategoryCount());
    statistic.setSubCategoryCount(getSubCategoryCount());
    statistic.setUserCountPerMonth(getCountOfUsersPerMouth());
    statistic.setCommissionPerMouths(getCommissionStats());
    return statistic;
  }

  @Transactional(readOnly = true)
  @Override
  public Statistic getStatisticByFilter(List<StatisticEnum> types) {
    Statistic statistic = new Statistic();
    for (StatisticEnum type : types) {
      switch (type) {
        case MONEY:
          statistic.setCommissionPerMouths(getCommissionStats());
          break;
        case CATEGORIES:
          statistic.setSubCategoryCount(getSubCategoryCount());
          break;
        default:
          throw new IllegalArgumentException("Statistic type[" + type.name() + "] doesn't exist.");
      }
    }
    return statistic;
  }

  @Override
  @Transactional(readOnly = true)
  public List<CommissionPerMouth> getCommissionStats() {
    List<CommissionPerMouthProjection> commissionPerMouth = paymentAuditRepository.getCommissionPerMouth();
    List<CommissionPerMouth> result = new ArrayList<>();
    for (CommissionPerMouthProjection commission : commissionPerMouth) {
      result.add(CommissionPerMouth.builder()
                     .amount(commission.getAmount())
                     .date(commission.getDate().replace(" ", ""))
                     .index(commission.getIndex())
                     .month(commission.getMonth().replace(" ", ""))
                     .build());
    }
    return result;
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserCountPerMonth> getCountOfUsersPerMouth() {
    List<UserCountPerMonthProjection> countListProjection = userRepository.countAllByMonth();
    List<UserCountPerMonth> countPerMonths = new ArrayList<>();


    for (UserCountPerMonthProjection e : countListProjection) {
      countPerMonths.add(new UserCountPerMonth(e.getIndex(),
                                               e.getDate(),
                                               e.getMonth(),
                                               e.getCount()));
    }

//    if (countPerMonths.size() != 12) {
//      countPerMonths = addNonExistentMonths(countPerMonths);
//    }
    return countPerMonths;
  }

  @Override
  @Transactional(readOnly = true)
  public List<CategoryCount> getCategoryCount() {
    List<CategoryCountProjection> listProjections = auctionEventRepository.getCountPerCategory();
    List<CategoryCount> list = new ArrayList<>();
    listProjections.forEach(e -> list.add(new CategoryCount(e.getName(), e.getCount())));
    return list;
  }

  @Override
  @Transactional(readOnly = true)
  public List<CategoryCount> getSubCategoryCount() {
    List<CategoryCountProjection> listProjections = auctionEventRepository.getCountPerSubCategory();
    List<CategoryCount> list = new ArrayList<>();
    listProjections.forEach(e -> list.add(new CategoryCount(e.getName(), e.getCount())));
    return list;
  }


//  private List<UserCountPerMonth> addNonExistentMonths(List<UserCountPerMonth> countPerMonths) {
//    List<Months> months = List.of(Months.values());
//    for (Months month : months) {
//      if (!countPerMonths.stream().anyMatch(e -> month.getLabel().equals(e.getMonth()))) {
//        countPerMonths.add(new UserCountPerMonth(month.getLabel(),
//                                                 month.getIndex(),));
//      }
//    }
//    return null;
//  }
}
