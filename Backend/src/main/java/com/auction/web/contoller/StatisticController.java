package com.auction.web.contoller;

import com.auction.service.interfaces.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:8081")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/statistic")
public class StatisticController {
  private final StatisticService statisticService;

  @GetMapping
  public ResponseEntity getStatistic() {
    return ResponseEntity.ok(statisticService.getSubCategoryCount());
  }
}
