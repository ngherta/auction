package com.auction.web.contoller;

import com.auction.web.dto.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
@CrossOrigin("http://localhost:8081")
public class WebSocketController {
  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public ResponseEntity<?> greeting(String message) {
    return ResponseEntity.ok(message);
  }
}
