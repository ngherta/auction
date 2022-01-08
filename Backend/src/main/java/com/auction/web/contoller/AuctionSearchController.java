package com.auction.web.contoller;

import com.auction.service.interfaces.AuctionEventService;
import com.auction.web.dto.AuctionEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:8081")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auction/search")
public class AuctionSearchController {
  private final AuctionEventService auctionEventService;

  @GetMapping
  public ResponseEntity<Page<AuctionEventDto>> getAuctionBySearch(@RequestParam("text") String text,
                                                                  @RequestParam(defaultValue = "1") int page,
                                                                  @RequestParam(defaultValue = "10") int perPage) {
    System.out.println("NGH: text ----" + text);
    return ResponseEntity.ok(auctionEventService.search(text, page, perPage));
  }
}
