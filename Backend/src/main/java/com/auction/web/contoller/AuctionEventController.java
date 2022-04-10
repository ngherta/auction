package com.auction.web.contoller;

import com.auction.model.enums.AuctionStatus;
import com.auction.service.interfaces.AuctionActionService;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.request.AuctionEventRequest;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auction")
public class AuctionEventController {

  private final AuctionEventService auctionEventService;
  private final AuctionActionService auctionActionService;

  @PostMapping
  @ApiOperation("Create new auction-event")
  public ResponseEntity<AuctionEventDto> createAuctionEvent(@Valid @RequestBody AuctionEventRequest request) {
    return ResponseEntity.ok(auctionEventService.save(request));
  }

  @GetMapping
  @ApiOperation("Get all auctions per page")
  public ResponseEntity<Page<AuctionEventDto>> getAll(@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int perPage) {
    Page<AuctionEventDto> pageOfAuctions = auctionEventService.get(page, perPage);
    return ResponseEntity.ok(pageOfAuctions);
  }

  @GetMapping("/filter")
  @ApiOperation("Filter auctions by title, categories or statuses")
  public ResponseEntity<Page<AuctionEventDto>> getAllByFilter(@RequestParam(defaultValue = "1") int page,
                                                              @RequestParam(defaultValue = "10") int perPage,
                                                              @RequestParam(defaultValue = "") String title,
                                                              @RequestParam(defaultValue = "") List<Long> categoryIds,
                                                              @RequestParam(defaultValue = "") List<AuctionStatus> statuses) {
    return ResponseEntity.ok(auctionEventService.findAllAndFilter(page,
                                                                  perPage,
                                                                  title,
                                                                  categoryIds,
                                                                  statuses));
  }


  @GetMapping("/all")
  @ApiOperation("Get all auctions")
  public ResponseEntity<List<AuctionEventDto>> getAll() {
    return ResponseEntity.ok(auctionEventService.findAll());
  }

  @GetMapping("/sort")
  @ApiOperation("Get sorting by popularity auctions")
  public ResponseEntity<Page<AuctionEventDto>> getAllAuctionByRating(@RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "10") int perPage) {
    return ResponseEntity.ok(auctionEventService.getAllSortByRating(page, perPage));
  }

  @DeleteMapping("/{auctionId}")
  @ApiOperation("Delete auction-event by id")
  public ResponseEntity<Void> deleteById(@PathVariable Long auctionId) {
    auctionEventService.deleteById(auctionId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{auctionId}")
  @ApiOperation("Get auction-event by id")
  public ResponseEntity<AuctionEventDto> getById(@PathVariable Long auctionId) {
    return ResponseEntity.ok(auctionEventService.getById(auctionId));
  }

  @PutMapping("/{auctionId}")
  @ApiOperation("Update auction-event by id")
  public ResponseEntity<AuctionEventDto> updateById(@PathVariable Long auctionId,
                                                    @Valid @RequestBody AuctionEventRequest request) {
    return ResponseEntity.ok(auctionEventService.update(request, auctionId));
  }

  @PutMapping("/block/{auctionId}")
  @ApiOperation("Block auction-event by id")
  public ResponseEntity<AuctionEventDto> block(@PathVariable Long auctionId) {
    return ResponseEntity.ok(auctionEventService.blockAuctionEventById(auctionId));
  }

  @GetMapping("/category/{subCategoryId}")
  @ApiOperation("Get auctions by category")
  public ResponseEntity<Page<AuctionEventDto>> getAuctionsByCategory(@PathVariable Long subCategoryId,
                                                                     @RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "10") int perPage) {
    return ResponseEntity.ok(auctionEventService.findAuctionsByCategory(subCategoryId, page, perPage));
  }

  @GetMapping("/participant/{userId}")
  @ApiOperation("Get auctions by participant")
  public ResponseEntity<Page<AuctionEventDto>> getAllByParticipant(@PathVariable Long userId,
                                                                         @RequestParam(defaultValue = "1") int page,
                                                                         @RequestParam(defaultValue = "10") int perPage) {
    return ResponseEntity.ok(auctionEventService.getAuctionsByParticipant(userId, page, perPage));
  }

  @GetMapping("/owner/{userId}")
  @ApiOperation("Get auctions by owner")
  public ResponseEntity<Page<AuctionEventDto>> getAllByOwner(@RequestParam(defaultValue = "1") int page,
                                                             @RequestParam(defaultValue = "10") int perPage,
                                                             @PathVariable Long userId) {
    return ResponseEntity.ok(auctionEventService.getAllByOwner(userId, page, perPage));
  }

  @GetMapping("/all/{userId}")
  @ApiOperation("Get all auctions where user participates or creates")
  public ResponseEntity<Page<AuctionEventDto>> getAllWhereContainsUser(@PathVariable Long userId){
      if (userId != null) {
        throw new NotYetImplementedException("Need to implement");
      }
    return ResponseEntity.ok().build();
  }
}