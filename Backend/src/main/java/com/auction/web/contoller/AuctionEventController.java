package com.auction.web.contoller;

import com.auction.service.interfaces.AuctionEventService;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.request.AuctionEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auction")
public class AuctionEventController {

    private final AuctionEventService auctionEventService;

    @PostMapping
    public ResponseEntity createAuctionEvent(@RequestBody @Valid AuctionEventRequest request) {
        return ResponseEntity.ok(auctionEventService.save(request));
    }

    @GetMapping
    public ResponseEntity getAll(@RequestParam(defaultValue = "1") int page,
                                 @RequestParam(defaultValue = "10") int perPage) {
        return ResponseEntity.ok(auctionEventService.get(page, perPage));
    }

    @GetMapping("/sort")
    public ResponseEntity getAllAuctionByRating() {
        return ResponseEntity.ok(auctionEventService.getAllSortByRating());
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteById(Long auctionId) {
        auctionEventService.deleteById(auctionId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{auctionId}")
    public ResponseEntity<AuctionEventDto> updateById(@PathVariable Long auctionId,
                                                      @RequestBody AuctionEventRequest request) {
        return ResponseEntity.ok(auctionEventService.update(request ,auctionId));
    }

    @PutMapping("/block/{auctionId}")
    public ResponseEntity block(@PathVariable Long auctionId) {
        return ResponseEntity.ok(auctionEventService.blockAuctionEventById(auctionId));
    }
}
