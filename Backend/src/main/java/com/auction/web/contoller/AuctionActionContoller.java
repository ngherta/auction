package com.auction.web.contoller;

import com.auction.config.jwt.JwtUtils;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.model.mapper.AuctionActionToDtoMapper;
import com.auction.service.interfaces.AuctionActionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/bids")
public class AuctionActionContoller {

  private final AuctionActionService auctionActionService;

  @GetMapping("/{auctionId}")
  public ResponseEntity getByAuctionId(@PathVariable Long auctionId) {
    return ResponseEntity.ok(auctionActionService.getAllByAuctionId(auctionId));
  }

  //create method for betting using websocket
}
