package com.auction.web.contoller;

import com.auction.dto.AuctionEventDto;
import com.auction.dto.ComplaintDto;
import com.auction.dto.request.AuctionEventRequest;
import com.auction.dto.request.ComplaintAdminRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.UserNotFoundException;
import com.auction.service.interfaces.ComplaintService;
import com.auction.web.model.AuctionEvent;
import com.auction.repository.AuctionEventRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.web.model.enums.AuctionStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.HtmlUtils;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/auction")
public class AuctionEventController {

    private final AuctionEventService auctionEventService;
    private final AuctionEventRepository auctionEventRepository;
    private final ComplaintService complaintService;

    @PostMapping()
    public ResponseEntity createAuctionEvent(@RequestBody AuctionEventRequest request) {
        AuctionEventDto auctionEventDto = auctionEventService.save(request);

        return ResponseEntity.ok(auctionEventDto);
    }

    @GetMapping()
    public List<AuctionEventDto> getAll() {
        return auctionEventService.getAll();
    }

    @GetMapping("/sort")
    public List<AuctionEventDto> getAllAuctionByRating() {
        return auctionEventService.getAllSortByRating();
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Hello, " + HtmlUtils.htmlEscape(message + "xq");
    }

    @DeleteMapping()
    public ResponseEntity deleteById(Long auctionId) throws AuctionEventNotFoundException {
        Optional<AuctionEvent> auctionEvent = auctionEventRepository.findById(auctionId);
        if (!auctionEvent.isPresent()) {
            throw new AuctionEventNotFoundException("Auction event[" + auctionId + "] doesn't exist");
        }
        auctionEventService.delete(auctionEvent.get());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{auctionId}")
    public ResponseEntity updateById(@PathVariable Long auctionId,
                                     @RequestBody AuctionEventRequest request) throws AuctionEventNotFoundException {
        AuctionEventDto auctionEventDto = auctionEventService.update(request ,auctionId);
        return ResponseEntity.ok(auctionEventDto);
    }

    @PutMapping("/block")
    public ResponseEntity block(@RequestBody ComplaintAdminRequest request) throws AuctionEventNotFoundException, UserNotFoundException {
        //if admin!

        ComplaintDto complaintDto = complaintService.blockAuction(request);

        return ResponseEntity.ok(complaintDto);
    }
}
