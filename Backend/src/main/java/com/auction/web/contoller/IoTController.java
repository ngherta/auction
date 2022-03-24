package com.auction.web.contoller;

import com.auction.service.interfaces.ButtonService;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.request.IotConnectionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/iot")
public class IoTController {
  private final ButtonService buttonService;
  private final SimpMessagingTemplate messagingTemplate;

  @PostMapping("/bet")
  public ResponseEntity<AuctionActionDto> defaultBet(@RequestBody String buttonId) {
    AuctionActionDto dto = buttonService.defaultBet(buttonId);
    messagingTemplate.convertAndSend("/betting/" + dto.getAuctionEvent(), dto);
    return ResponseEntity.ok(dto);
  }

  @PostMapping("/finish")
  public ResponseEntity<AuctionActionDto> finishBet(@RequestBody String buttonId) {
    AuctionActionDto dto = buttonService.finishAuction(buttonId);
    messagingTemplate.convertAndSend("/betting/" + dto.getAuctionEvent(), dto);
    return ResponseEntity.ok(dto);
  }

  @PutMapping("/connect")
  public ResponseEntity<Void> connect(@RequestBody IotConnectionRequest request) {
    buttonService.connect(request.getUserId(), request.getAuctionId());
    return ResponseEntity.ok().build();
  }

  @PutMapping("/disconnect")
  public ResponseEntity<Void> disconnect(@RequestParam Long userId) {
    buttonService.disconnect(userId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/auction")
  public ResponseEntity<Boolean> isConnected(@RequestParam Long userId,
                                             @RequestParam Long auctionId) {
    return ResponseEntity.ok(buttonService.isConnected(userId, auctionId));
  }
}