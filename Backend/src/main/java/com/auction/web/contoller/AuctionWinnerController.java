package com.auction.web.contoller;

import com.auction.service.interfaces.AuctionWinnerService;
import com.auction.web.dto.AuctionWinnerDto;
import com.auction.web.dto.request.AddAddressToWinnerRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;


@RestController
@RequestMapping("/api/winner")
@RequiredArgsConstructor
public class AuctionWinnerController {
  private final AuctionWinnerService auctionWinnerService;

  @GetMapping("/user/{userId}")
  public ResponseEntity<Page<AuctionWinnerDto>> getAuctionWinnersForUser(@PathVariable Long userId,
                                                                         @RequestParam(defaultValue = "1") int page,
                                                                         @RequestParam(defaultValue = "10") int perPage) {
    return ResponseEntity.ok(auctionWinnerService.getAllAuctionWinnerForUser(userId, page, perPage));
  }

  @GetMapping("/user/creator/{userId}")
  public ResponseEntity<Page<AuctionWinnerDto>> getAuctionWinnersByUserCreator(@PathVariable Long userId,
                                                                         @RequestParam(defaultValue = "1") int page,
                                                                         @RequestParam(defaultValue = "10") int perPage) {
    return ResponseEntity.ok(auctionWinnerService.getAllAuctionWinnerForUserCreator(userId, page, perPage));
  }

  @PostMapping("/{auctionId}/address/default")
  public ResponseEntity<Void> useDefaultAddress(@PathVariable Long auctionId) {
    auctionWinnerService.useDefaultAddress(auctionId);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{auctionId}/address")
  public ResponseEntity<Void> addAddress(@RequestBody AddAddressToWinnerRequest request) {
    auctionWinnerService.addAddress(request);
    return ResponseEntity.ok().build();
  }

  @PostMapping("/{auctionId}/track/{trackNumber}")
  public ResponseEntity<Void> addTrackNumber(@PathVariable Long auctionId,
                                             @NotBlank @PathVariable String trackNumber) {
    auctionWinnerService.addTrackNumber(auctionId, trackNumber);
    return ResponseEntity.ok().build();
  }

  @PutMapping("/{auctionId}/delivered")
  public ResponseEntity<Void> delivered(@PathVariable Long auctionId) {
    auctionWinnerService.finishDeliveryByAuctionId(auctionId);
    return ResponseEntity.ok().build();
  }

}
