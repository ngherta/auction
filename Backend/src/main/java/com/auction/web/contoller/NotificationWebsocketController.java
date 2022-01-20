package com.auction.web.contoller;

import com.auction.service.interfaces.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@Slf4j
@CrossOrigin("http://localhost:8081")
@RequiredArgsConstructor
public class NotificationWebsocketController {
  private final SimpMessagingTemplate messagingTemplate;
  private final NotificationService notificationService;

  @MessageMapping("/notification")
  public void receiveSeenOfNotification(@Header Long userId) {

    messagingTemplate.convertAndSend("/notification/" + userId);
  }


}
