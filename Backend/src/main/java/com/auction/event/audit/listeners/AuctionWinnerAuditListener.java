package com.auction.event.audit.listeners;

import com.auction.event.audit.AuctionWinnerAuditEvent;
import com.auction.service.audit.AuctionWinnerAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import static java.lang.Boolean.TRUE;

@RequiredArgsConstructor
@Component
public class AuctionWinnerAuditListener {
  private final AuctionWinnerAuditService auctionWinnerAuditService;

  @EventListener(AuctionWinnerAuditEvent.class)
  public void sendComplaintNotification(AuctionWinnerAuditEvent event) {
    if (event.getAsync() == TRUE) {
      asyncMethod(event);
    }
    else {
      auctionWinnerAuditService.create(event.getAudit());
    }
  }

  @Async
  public void asyncMethod(AuctionWinnerAuditEvent event) {
    auctionWinnerAuditService.create(event.getAudit());
  }

}
