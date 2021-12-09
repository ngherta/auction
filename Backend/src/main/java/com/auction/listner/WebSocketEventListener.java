package com.auction.listner;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
  private final SimpMessageSendingOperations messagingTemplate;

  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
    StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

    log.info(event + " || " + event.getMessage());
    System.out.println(event.getMessage());
    String username = (String) headerAccessor.getSessionAttributes().get("username");
    if (username != null) {


      messagingTemplate.convertAndSend("/topic/chat", "");
    }
  }
}
