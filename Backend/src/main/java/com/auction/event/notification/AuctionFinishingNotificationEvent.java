package com.auction.event.notification;

import com.auction.model.AuctionWinner;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Getter
@Builder
@Slf4j
public class AuctionFinishingNotificationEvent extends ApplicationEvent {
  private final transient AuctionWinner auctionWinner;

  public AuctionFinishingNotificationEvent(AuctionWinner auctionWinner) {
    super(auctionWinner);
    this.auctionWinner = auctionWinner;
    log.info("Created AuctionFinishingNotificationEvent for: Auction [{}]", auctionWinner.getId());
  }
}
