package com.auction.web.contoller;

import com.auction.service.interfaces.ComplaintService;
import com.auction.web.dto.ComplaintAuditDto;
import com.auction.web.dto.ComplaintDto;
import com.auction.web.dto.request.ComplaintAdminRequest;
import com.auction.web.dto.request.ComplaintRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/complaint")
public class ComplaintController {
  private final ComplaintService complaintService;

  @GetMapping
  public ResponseEntity<List<ComplaintDto>> getAll(){
    return ResponseEntity.ok(complaintService.getAll());
  }

  @PostMapping("/answer")
  public ResponseEntity<ComplaintAuditDto> satisfyComplaint(@RequestBody ComplaintAdminRequest request) {
    return ResponseEntity.ok(complaintService.satisfyComplaint(request));
  }

  @PostMapping
  public ResponseEntity<ComplaintDto> create(@RequestBody ComplaintRequest request) {
    return ResponseEntity.ok(complaintService.create(request));
  }
}
