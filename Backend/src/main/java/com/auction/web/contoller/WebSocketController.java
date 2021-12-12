package com.auction.web.contoller;

import com.auction.service.interfaces.AuctionActionService;
import com.auction.web.dto.AuctionActionDto;
import com.auction.web.dto.request.BetRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@CrossOrigin("http://localhost:8081")
@RequiredArgsConstructor
public class WebSocketController {
  private final AuctionActionService auctionActionService;
  private final SimpMessagingTemplate messagingTemplate;

  @MessageMapping("/betting/{auctionId}")
  public String betting(@DestinationVariable("auctionId") Long auctionId, @Valid BetRequest betRequest) {
    log.info(betRequest.toString());
    AuctionActionDto dto = auctionActionService.bet(betRequest.getBet(),
                                                    betRequest.getAuctionId(),
                                                    betRequest.getUserId());
    messagingTemplate.convertAndSend("/betting/" + auctionId, dto);
    return null;
  }

  @MessageExceptionHandler
  @SendToUser("/betting/errors")
  public String handleException(Throwable exception) {
    return exception.getMessage();
  }
}
