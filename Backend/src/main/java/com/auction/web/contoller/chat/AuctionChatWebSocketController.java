package com.auction.web.contoller.chat;

import com.auction.service.interfaces.AuctionChatService;
import com.auction.web.dto.ChatMessageDto;
import com.auction.web.dto.request.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
@RequiredArgsConstructor
public class AuctionChatWebSocketController {

  private final AuctionChatService auctionChatService;
  private final SimpMessagingTemplate messagingTemplate;


  @MessageMapping("/chat/auction/{auctionId}")
  public void createAuctionChatMessage(@DestinationVariable("auctionId") Long auctionId,
                                       @RequestBody ChatMessageRequest request) {
    ChatMessageDto dto = auctionChatService.send(request, auctionId);
    messagingTemplate.convertAndSend("/chat/auction/" + auctionId, dto);
  }
}
