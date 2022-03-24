package com.auction.event.notification;

import com.auction.model.AuctionEventComplaint;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

@Getter
@Builder
@Slf4j
public class ComplaintCreateNotificationEvent  extends ApplicationEvent {

  private final transient AuctionEventComplaint complaint;

  public ComplaintCreateNotificationEvent(AuctionEventComplaint complaint) {
    super(complaint);
    this.complaint = complaint;
    log.info("Created ComplaintCreateNotificationEvent for: AuctionEventComplaint [{}]", complaint.getId());
  }
}
