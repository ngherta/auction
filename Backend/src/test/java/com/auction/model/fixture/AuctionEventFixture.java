package com.auction.model.fixture;

import com.auction.model.AuctionEvent;
import com.auction.model.enums.AuctionStatus;
import com.auction.model.enums.AuctionType;

import java.time.LocalDateTime;

public class AuctionEventFixture {

  private static final String title = "Air Jordan";
  private static final String description = "Nice!";
  private static final AuctionStatus status = AuctionStatus.EXPECTATION;
  private static final AuctionType type = AuctionType.COMMERCIAL;
  private static final Double startPrice = 10D;
  private static final Double finishPrice = 100D;
  private static final Double charityPercent = 0D;
  private static final LocalDateTime startDate = LocalDateTime.now().minusDays(-1);
  private static final LocalDateTime finishDate = LocalDateTime.now().plusDays(1);

  public static AuctionEvent auctionEvent() {
    return AuctionEvent.builder()
            .user(UserFixture.user())
            .title(title)
            .description(description)
            .statusType(status)
            .genDate(LocalDateTime.now())
            .auctionType(type)
            .startPrice(startPrice)
            .finishPrice(finishPrice)
            .charityPercent(charityPercent)
            .startDate(startDate)
            .finishDate(finishDate)
            .categories(SubCategoriesFixture.listOfCategories())
            .build();
  }
}
