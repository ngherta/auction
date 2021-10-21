package com.auction.contoller;

import com.auction.dto.AuctionEventDto;
import com.auction.dto.request.AuctionEventRequest;
import com.auction.service.interfaces.AuctionEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auction")
public class AuctionEventController {

    private final AuctionEventService auctionEventService;

    @PostMapping()
    public ResponseEntity createAuctionEvent(@RequestBody AuctionEventRequest request) {
        AuctionEventDto auctionEventDto = auctionEventService.save(request);

        return ResponseEntity.ok(auctionEventDto);
    }

}
