package com.auction.web.contoller;

import com.auction.service.interfaces.NotificationMessageService;
import com.auction.web.dto.request.ListOfLongs;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@Slf4j
@CrossOrigin("http://localhost:8081")
@RequiredArgsConstructor
public class NotificationWebsocketController {
  private final NotificationMessageService notificationMessageService;

  @MessageMapping("/notification/{userId}")
  public void seenNotification(@DestinationVariable("userId") Long userId,
                               @RequestBody ListOfLongs list) {
    notificationMessageService.seen(userId, list.getList());
  }
}
