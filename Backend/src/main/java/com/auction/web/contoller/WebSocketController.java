package com.auction.web.contoller;

import com.auction.service.interfaces.AuctionActionService;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.request.BetRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@Slf4j
@CrossOrigin("http://localhost:8081")
@RequiredArgsConstructor
public class WebSocketController {
  private final AuctionActionService auctionActionService;
  private final SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/hello")
//  @SendTo("/topic/betting")
  public void greeting(BetRequest betRequest) {
    log.info(betRequest.toString());
    log.info("token = {}, auctionId = {}, net {}", betRequest.getUserId(), betRequest.getAuctionId(), betRequest.getBet());
    AuctionActionDto dto = auctionActionService.bet(betRequest.getBet(),
                                                    betRequest.getAuctionId(),
                                                    betRequest.getUserId());
    messagingTemplate.convertAndSendToUser(
            betRequest.getAuctionId().toString(),"/queue/messages",
            dto);
  }
}
