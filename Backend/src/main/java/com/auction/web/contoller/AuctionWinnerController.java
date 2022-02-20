package com.auction.web.contoller;

import com.auction.service.interfaces.AuctionWinnerService;
import com.auction.web.dto.AuctionWinnerDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:8081")
@RestController
@RequestMapping("/api/winner")
@RequiredArgsConstructor
public class AuctionWinnerController {
  private final AuctionWinnerService auctionWinnerService;

  @GetMapping("/user/{userId}")
  public ResponseEntity<Page<AuctionWinnerDto>> getAuctionWinnersForUser(@PathVariable Long userId,
                                                                         @RequestParam(defaultValue = "1") int page,
                                                                         @RequestParam(defaultValue = "10") int perPage) {

    return ResponseEntity.ok(auctionWinnerService.getAllAuctionWinnerForUser(userId, page, perPage));
  }

}
