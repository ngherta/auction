package com.auction.helper;

import com.auction.repository.AuctionEventRepository;
import com.auction.service.interfaces.AuctionEventService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ScheduledTasks {

  private final AuctionEventService auctionEventService;
  private final AuctionEventRepository auctionEventRepository;

  @Scheduled(fixedDelay = 2000)
  public void checkEventForFinish() {
    List<Long> list = auctionEventRepository.getListForChangeStatus();
    log.info("Try to find auction events to finish it.");
    if (!list.isEmpty()) {
      log.info("Found auction events for finishing:");
      for (Long id : list) {
        log.info("[" + id + "] try to finish it.");
      }
      auctionEventService.changeStatusToFinished(list);
    }
  }
}