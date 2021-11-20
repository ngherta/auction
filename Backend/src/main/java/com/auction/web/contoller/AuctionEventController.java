package com.auction.web.contoller;

import com.auction.model.mapper.AuctionEventToDtoMapper;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.request.AuctionEventRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.StartPriceNullException;
import com.auction.repository.AuctionEventRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.model.AuctionEvent;
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
    private final AuctionEventToDtoMapper auctionEventToDtoMapper;

    @PostMapping()
    public ResponseEntity createAuctionEvent(@RequestBody AuctionEventRequest request) throws StartPriceNullException {
        AuctionEvent auctionEvent = auctionEventService.save(request);

        return ResponseEntity.ok(auctionEventToDtoMapper.map(auctionEvent));
    }

    @GetMapping()
    public ResponseEntity getAll() {
        return ResponseEntity.ok(auctionEventToDtoMapper.mapList(auctionEventService.getAll()));
    }

    @GetMapping("/sort")
    public ResponseEntity getAllAuctionByRating() {
        return ResponseEntity.ok(auctionEventToDtoMapper.mapList(auctionEventService.getAllSortByRating()));
    }

    @MessageMapping("/hello")
    @SendTo("/topic/greetings")
    public String greeting(String message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return "Hello, " + HtmlUtils.htmlEscape(message + "xq");
    }

    @DeleteMapping()
    public ResponseEntity deleteById(Long auctionId) throws AuctionEventNotFoundException {
        auctionEventService.deleteById(auctionId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{auctionId}")
    public ResponseEntity updateById(@PathVariable Long auctionId,
                                     @RequestBody AuctionEventRequest request) throws AuctionEventNotFoundException {
        AuctionEvent auctionEvent = auctionEventService.update(request ,auctionId);
        return ResponseEntity.ok(auctionEventToDtoMapper.map(auctionEvent));
    }

    @PutMapping("/block/{auctionId}")
    public ResponseEntity block(@PathVariable Long auctionId) throws AuctionEventNotFoundException {

        Optional<AuctionEvent> auctionEvent = auctionEventRepository.findById(auctionId);
        if (auctionEvent.isEmpty()) {
            throw new AuctionEventNotFoundException("AuctionEvent[" + auctionId + "doesn't exist.");
        }
//        AuctionEventDto auctionEventDto = AuctionEventDto.from(auctionEventService.blockAuctionEvent(auctionEvent.get()));

//        ComplaintDto complaintDto = complaintService.blockAuction();

        return ResponseEntity.ok().build();
    }

    //WEBSOKET
    @MessageMapping("/searching")
    @SendTo("/topic/search")
    public String search(String message) throws Exception {
        auctionEventService.search(message);
        return "Hello, " + HtmlUtils.htmlEscape(message + "xq");
    }
}
