package com.auction.web.contoller;

import com.auction.service.interfaces.AuctionActionService;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.response.LastBidResponse;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://34.140.181.128:8082")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bids")
public class AuctionActionController {

  private final AuctionActionService auctionActionService;

  @GetMapping("/{auctionId}")
  @ApiOperation("Get auction-event by id")
  public ResponseEntity<List<AuctionActionDto>> getByAuctionId(@PathVariable Long auctionId) {
    return ResponseEntity.ok(auctionActionService.getAllByAuctionId(auctionId));
  }

  @GetMapping("/last")
  @ApiOperation("Get last bid for auction")
  public ResponseEntity<List<LastBidResponse>> getLastBidForAuctions(@RequestParam("auctionIds") List<Long> listOfIds) {
    return ResponseEntity.ok(auctionActionService.getLastBidForAuction(listOfIds));
  }

  @PostMapping("/create/iot")
  public ResponseEntity<Void> betByIoT(String terminal) {
    auctionActionService.bet(terminal);
    return ResponseEntity.ok().build();
  }
}
