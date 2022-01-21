package com.auction.listener;

import com.auction.helper.UserSessionCache;
import com.auction.service.interfaces.NotificationGenerationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketEventListener {
  private final UserSessionCache userServiceCache;
  private final NotificationGenerationService notificationGenerationService;


  @EventListener
  public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {

  }

  @EventListener
  public void handleWebSocketConnectListener(SessionConnectedEvent event) {

  }

  @EventListener
  public void handleSessionSubscribeEvent(SessionSubscribeEvent event) {
    GenericMessage message = (GenericMessage) event.getMessage();
    String simpDestination = (String) message.getHeaders().get("simpDestination");

    log.info("New connection to: {}", simpDestination);

    if (simpDestination.startsWith("/notification/")) {
      StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());

      String userId = simpDestination.replace("/notification/", "")
              .replace("/secured/user", "");
      String sessionId = headerAccessor.getHeader("simpSessionId").toString();
      if (!userId.isEmpty() && !sessionId.isEmpty()) {
        log.info("User[{}] with session[{}] connected!", userId, sessionId);
        userServiceCache.put(sessionId, userId);
        notificationGenerationService.initNotificationsForUser(Long.parseLong(userId));
      }
    }
  }

  @EventListener
  public void handleSessionUnsubscribeEvent(SessionUnsubscribeEvent event) {
    GenericMessage message = (GenericMessage) event.getMessage();
    String simpDestination = (String) message.getHeaders().get("simpDestination");

    log.info("Disconnect from: {}", simpDestination);

    if (simpDestination.startsWith("/notification/")) {
      StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage());
      String sessionId = headerAccessor.getHeader("simpSessionId").toString();
      log.info("User disconnected from {} with session[{}]", simpDestination, sessionId);
      if (!sessionId.isEmpty()) {
        userServiceCache.remove(sessionId);
      }
    }
  }

}
