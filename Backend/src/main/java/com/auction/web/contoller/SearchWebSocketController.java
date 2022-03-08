package com.auction.web.contoller;

import com.auction.service.interfaces.AuctionEventService;
import com.auction.web.dto.AuctionSearchDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.Valid;
import java.util.List;

@Controller
@Slf4j
@CrossOrigin("http://localhost:8081")
@RequiredArgsConstructor
public class SearchWebSocketController {
    private final AuctionEventService auctionEventService;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/search/{destination}")
    public void search(@DestinationVariable("destination") String destination,
                                         @Valid String message) {
        List<AuctionSearchDto> result = auctionEventService.reactiveSearch(message);
        messagingTemplate.convertAndSend("/search/" + destination, result);
    }
}
