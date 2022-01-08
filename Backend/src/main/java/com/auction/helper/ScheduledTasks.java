package com.auction.helper;

import com.auction.cache.UserSessionCache;
import com.auction.model.AuctionEvent;
import com.auction.model.enums.AuctionStatus;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.AuctionEventSortService;
import com.auction.service.interfaces.NotificationSenderService;
import com.auction.service.interfaces.TokenConfirmationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

  private final AuctionEventService auctionEventService;
  private final AuctionEventSortService auctionEventSortService;
  private final UserSessionCache userServiceCache;
  private final NotificationSenderService notificationService;
  private final TokenConfirmationService tokenConfirmationService;

  @Scheduled(cron = "0 0/1 * * * ?")
  public void checkEventForFinish() throws MessagingException, UnsupportedEncodingException {
    List<AuctionEvent> list = auctionEventService.getListForFinish(AuctionStatus.ACTIVE);
    log.info("Try to find auction events to finish it.");
    if (!list.isEmpty()) {
      log.info("Found auction events for finishing.");
      auctionEventService.changeStatusToFinished(list);
    }
  }

  @Scheduled(fixedDelay = 2000)
  public void getActiveUser() {
    Set<String> activeUsers = userServiceCache.getActiveUsers();
  }

  @Scheduled(fixedDelay = 60000)
  public void deleteUnconfirmedUsers() {
    tokenConfirmationService.deleteUnconfirmedUsers();
  }

  @Scheduled(cron = "0 0/1 * * * ?")
  public void checkEventForStart() {
    List<AuctionEvent> list = auctionEventService.getListForStart(AuctionStatus.EXPECTATION);
    log.info("Try to find auction events to start it.");
    if (!list.isEmpty()) {
      list.forEach(e -> log.info("Found auction events for starting - auctionEvent[{}]", e.getId()));
      auctionEventService.changeStatusToStart(list);
    }
  }

  @Scheduled(fixedDelay = 100000)
  public void changeOrderForEvents(){
    log.info("Start sorting AuctionEvents...");
    auctionEventSortService.sortAuctionEvent();
    log.info("Finish sorting AuctionEvents...");
  }
}