package com.auction.web.contoller;

import com.auction.service.interfaces.AuctionActionService;
import com.auction.web.dto.AuctionActionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bids")
public class AuctionActionController {

  private final AuctionActionService auctionActionService;

  @GetMapping("/{auctionId}")
  public ResponseEntity<List<AuctionActionDto>> getByAuctionId(@PathVariable Long auctionId) {
    return ResponseEntity.ok(auctionActionService.getAllByAuctionId(auctionId));
  }

  //create method for betting using websocket
}
