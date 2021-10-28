package com.auction.helper;

import com.auction.model.AuctionEvent;
import com.auction.repository.AuctionEventRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.AuctionEventSortService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

  private final AuctionEventService auctionEventService;
  private final AuctionEventRepository auctionEventRepository;
  private final AuctionEventSortService auctionEventSortService;

  @Scheduled(fixedDelay = 20000)
  public void checkEventForFinish() {
    List<AuctionEvent> list = auctionEventRepository.getListForChangeStatus();
    log.info("Try to find auction events to finish it.");
    if (!list.isEmpty()) {
      log.info("Found auction events for finishing.");
      auctionEventService.changeStatusToFinished(list);
    }
  }

  @Scheduled(fixedDelay = 60000)
  public void changeOrderForEvents(){
    log.info("Start sorting AuctionEvents...");
    auctionEventSortService.sortAuctionEvent();
    log.info("Finish sorting AuctionEvents...");
  }
}