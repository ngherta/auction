package com.auction.event.notification;

import com.auction.model.AuctionAction;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Getter
@Builder
@Slf4j
public class ChangeBetEvent extends ApplicationEvent {

  private final transient AuctionAction action;

  public ChangeBetEvent(AuctionAction action) {
    super(action);
    this.action = action;
    log.info("Created ChangeBetEvent for: AuctionAction [{}]", action.getId());
  }
}
