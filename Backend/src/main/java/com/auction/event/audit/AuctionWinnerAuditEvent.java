package com.auction.event.audit;

import com.auction.model.AuctionWinner;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Getter
@Builder
@Slf4j
public class AuctionWinnerAuditEvent extends ApplicationEvent {

  private final transient AuctionWinner audit;
  private final transient Boolean async;

  public AuctionWinnerAuditEvent(AuctionWinner winner, boolean async) {
    super(winner);
    this.audit = winner;
    this.async = async;
    log.info("Created ComplaintNotificationEvent for: AuctionWinnerAudit [{}]", audit.getId());
  }
}
