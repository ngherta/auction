package com.auction.web.contoller;

import com.auction.service.interfaces.AuctionEventService;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.request.AuctionEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/auction")
public class AuctionEventController {

    private final AuctionEventService auctionEventService;
    //NGH
    @PostMapping
    public ResponseEntity createAuctionEvent(@RequestBody @Valid AuctionEventRequest request) {
        return ResponseEntity.ok(auctionEventService.save(request));
    }
//TODO: pageable
    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(auctionEventService.getAll());
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
