package com.auction.web.contoller;

import com.auction.dto.AuctionActionDto;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.repository.AuctionEventRepository;
import com.auction.service.interfaces.AuctionActionService;
import com.auction.web.model.AuctionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/bids")
public class AuctionActionContoller {

  private final AuctionActionService auctionActionService;
  private final AuctionEventRepository auctionEventRepository;

  @GetMapping("/{auctionId}")
  public ResponseEntity getByAuctionId(@PathVariable Long auctionId) throws AuctionEventNotFoundException {
    Optional<AuctionEvent> auctionEvent = auctionEventRepository.findById(auctionId);
    if (auctionEvent.isEmpty()) {
      throw new AuctionEventNotFoundException("AuctionEvent[" + auctionId + "] doesn't exist.");
    }
    List<AuctionActionDto> list = auctionActionService.getAllByAuction(auctionEvent.get());

    return ResponseEntity.ok(list);
  }
}
