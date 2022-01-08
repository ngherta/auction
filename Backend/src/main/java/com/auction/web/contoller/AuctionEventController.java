package com.auction.web.contoller;

import com.auction.model.AuctionEvent;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.specification.AuctionEventSpecification;
import com.auction.repository.specification.builder.AuctionSpecificationBuilder;
import com.auction.service.interfaces.AuctionActionService;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.web.dto.AuctionEventDto;
import com.auction.web.dto.request.AuctionEventRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@CrossOrigin("http://localhost:8081")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auction")
public class AuctionEventController {

  private final AuctionEventService auctionEventService;
  private final AuctionActionService auctionActionService;
  private final AuctionEventRepository auctionEventRepository;
  private final Mapper<AuctionEvent, AuctionEventDto> auctionEventToDtoMapper;


  @PostMapping
  public ResponseEntity<AuctionEventDto> createAuctionEvent(@Valid @RequestBody AuctionEventRequest request) {
    return ResponseEntity.ok(auctionEventService.save(request));
  }

  @GetMapping
  public ResponseEntity<Page<AuctionEventDto>> getAll(@RequestParam(defaultValue = "1") int page,
                                                      @RequestParam(defaultValue = "10") int perPage,
                                                      @RequestParam List<Long> subCategoryIds,
                                                      @RequestParam(defaultValue = "") String searchText) {
    Page<AuctionEventDto> pageOfAuctions = auctionEventService.get(page, perPage, subCategoryIds);
    return ResponseEntity.ok(pageOfAuctions);
  }

  @GetMapping("/filter")
  public ResponseEntity<Page<AuctionEventDto>> getAllByFilter(@RequestParam(defaultValue = "1") int page,
                                                              @RequestParam(defaultValue = "10") int perPage,
                                                              @RequestParam(value = "search") String search) {
    AuctionSpecificationBuilder builder = new AuctionSpecificationBuilder();
    Pattern pattern = Pattern.compile("(\\w+?)(:|<|>|::)(\\w+?),");
    Matcher matcher = pattern.matcher(search + ",");
    while (matcher.find()) {
      if (matcher.group(1).equalsIgnoreCase("category")) {
        builder.with(matcher.group(1), matcher.group(2), matcher.group(3), true);
      }
      else {
        builder.with(matcher.group(1), matcher.group(2), matcher.group(3), false);
      }
    }

    Pageable pageable = PageRequest.of(page - 1, perPage);


    Specification<AuctionEvent> specification = builder.build();
    Page<AuctionEventDto> pageOfAuctions = auctionEventRepository.findAll(specification, pageable).map(auctionEventToDtoMapper::map);

    return ResponseEntity.ok(pageOfAuctions);
  }

  @GetMapping("/all")
  public ResponseEntity<List<AuctionEventDto>> getAll() {
    return ResponseEntity.ok(auctionEventService.findAll());
  }

  @GetMapping("/sort")
  public ResponseEntity<Page<AuctionEventDto>> getAllAuctionByRating(@RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "10") int perPage) {
    return ResponseEntity.ok(auctionEventService.getAllSortByRating(page, perPage));
  }

  @DeleteMapping("/{auctionId}")
  public ResponseEntity<Void> deleteById(@PathVariable Long auctionId) {
    auctionEventService.deleteById(auctionId);
    return ResponseEntity.ok().build();
  }

  @GetMapping("/{auctionId}")
  public ResponseEntity<AuctionEventDto> getById(@PathVariable Long auctionId) {
    return ResponseEntity.ok(auctionEventService.getById(auctionId));
  }

  @PutMapping("/{auctionId}")
  public ResponseEntity<AuctionEventDto> updateById(@PathVariable Long auctionId,
                                                    @Valid @RequestBody AuctionEventRequest request) {
    return ResponseEntity.ok(auctionEventService.update(request, auctionId));
  }

  @PutMapping("/block/{auctionId}")
  public ResponseEntity<AuctionEventDto> block(@PathVariable Long auctionId) {
    return ResponseEntity.ok(auctionEventService.blockAuctionEventById(auctionId));
  }

  @GetMapping("/category/{subCategoryId}")
  public ResponseEntity<Page<AuctionEventDto>> getAuctionsByCategory(@PathVariable Long subCategoryId,
                                                                     @RequestParam(defaultValue = "1") int page,
                                                                     @RequestParam(defaultValue = "10") int perPage) {
    return ResponseEntity.ok(auctionEventService.findAuctionsByCategory(subCategoryId, page, perPage));
  }
}