package com.auction.helper;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

  @Scheduled(fixedDelay = 100000000)
  public void check() {
    //not implemented
  }
}