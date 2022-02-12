package com.auction.event.notification;

import com.auction.model.AuctionEventComplaintAudit;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Getter
@Builder
@Slf4j
public class ComplaintNotificationEvent extends ApplicationEvent {

  private AuctionEventComplaintAudit audit;

  public ComplaintNotificationEvent(AuctionEventComplaintAudit audit) {
    super(audit);
    this.audit = audit;
    log.info("Created ComplaintNotificationEvent for: ComplaintAudit [{}]", audit.getId());
  }
}
