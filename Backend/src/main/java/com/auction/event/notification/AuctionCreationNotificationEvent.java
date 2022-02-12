package com.auction.event.notification;

import com.auction.model.AuctionEvent;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Getter
@Builder
@Slf4j
public class AuctionCreationNotificationEvent extends ApplicationEvent {

  private final transient AuctionEvent auctionEvent;

  public AuctionCreationNotificationEvent(AuctionEvent auctionEvent) {
    super(auctionEvent);
    this.auctionEvent = auctionEvent;
    log.info("Created AuctionCreationNotificationEvent for: Auction [{}]", auctionEvent.getId());
  }
}
