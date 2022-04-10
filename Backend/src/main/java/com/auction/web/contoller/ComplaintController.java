package com.auction.web.contoller;

import com.auction.service.interfaces.ComplaintService;
import com.auction.web.dto.ComplaintAuditDto;
import com.auction.web.dto.ComplaintDto;
import com.auction.web.dto.request.ComplaintAdminRequest;
import com.auction.web.dto.request.ComplaintRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/complaint")
public class ComplaintController {
  private final ComplaintService complaintService;

  @GetMapping
  public ResponseEntity<Page<ComplaintDto>> getAll(@RequestParam(defaultValue = "1") int page,
                                                   @RequestParam(defaultValue = "10") int perPage) {
    return ResponseEntity.ok(complaintService.getAll(page, perPage));
  }

  @PostMapping("/answer")
  public ResponseEntity<ComplaintAuditDto> satisfyComplaint(@Valid @RequestBody ComplaintAdminRequest request) {
    return ResponseEntity.ok(complaintService.satisfyComplaint(request));
  }

  @PostMapping
  public ResponseEntity<ComplaintDto> create(@Valid @RequestBody ComplaintRequest request) {
    return ResponseEntity.ok(complaintService.create(request));
  }
}
