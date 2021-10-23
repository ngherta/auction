package com.auction.helper;

import com.auction.repository.AuctionEventRepository;
import com.auction.service.interfaces.AuctionEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

  private final AuctionEventService auctionEventService;
  private final AuctionEventRepository auctionEventRepository;

  @Scheduled(fixedDelay = 500)
  public void checkEventForFinish() {
    List<Long> list = auctionEventRepository.getListForChangeStatus();
    System.out.println("ScheduledTasks.checkEventForFinish: Try to find auction events to finish it.");
    if (!list.isEmpty()) {
      System.out.println("ScheduledTasks.checkEventForFinish: Found auction events for finishing:");
      for (Long id : list) {
        System.out.println("ScheduledTasks.checkEventForFinish: [" + id + "] try to finish it.");
      }
      auctionEventService.changeStatusToFinished(list);
    }
  }
}