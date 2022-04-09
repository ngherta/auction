package com.auction.web.contoller;

import com.auction.model.enums.ImageLinkType;
import com.auction.service.interfaces.ImageLinkService;
import com.auction.web.dto.ImageLinkDto;
import com.auction.web.dto.request.ImageLinkCreateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

//@CrossOrigin("http://34.140.181.128:8082")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/links")
public class ImageLinkController {
  private final ImageLinkService imageLinkService;

  @GetMapping("/{type}")
  public ResponseEntity<List<ImageLinkDto>> getAllByType(@PathVariable ImageLinkType type) {
    return ResponseEntity.ok(imageLinkService.findAllBy(type));
  }

  @PostMapping
  public ResponseEntity<ImageLinkDto> create(@Valid @RequestBody ImageLinkCreateRequest request) {
    return ResponseEntity.ok(imageLinkService.save(request));
  }
}
