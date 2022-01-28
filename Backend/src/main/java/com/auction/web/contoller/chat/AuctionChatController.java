package com.auction.web.contoller.chat;

import com.auction.service.interfaces.AuctionChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:8081")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auction/chat")
public class AuctionChatController {

  private final AuctionChatService auctionChatService;

  @GetMapping("/{id}")
  public ResponseEntity findMessagesByAuctionId(@PathVariable Long id) {
    return ResponseEntity.ok(auctionChatService.getAllByAuctionId(id));
  }
}
