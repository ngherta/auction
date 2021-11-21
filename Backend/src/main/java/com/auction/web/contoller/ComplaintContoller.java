package com.auction.web.contoller;

import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.UserNotFoundException;
import com.auction.model.mapper.ComplaintAuditToDtoMapper;
import com.auction.model.mapper.ComplaintToDtoMapper;
import com.auction.service.interfaces.ComplaintService;
import com.auction.web.dto.request.ComplaintAdminRequest;
import com.auction.web.dto.request.ComplaintRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequiredArgsConstructor
@RequestMapping("/complaint")
public class ComplaintContoller {
  private final ComplaintService complaintService;

  @GetMapping
  public ResponseEntity getAll(){
    return ResponseEntity.ok(complaintService.getAll());
  }

  @PostMapping("/admin")
  public ResponseEntity satisfyComplaint(@RequestBody ComplaintAdminRequest request) {
    return ResponseEntity.ok(complaintService.satisfyComplaint(request));
  }

  @PostMapping
  public ResponseEntity create(@RequestBody ComplaintRequest request) {
    return ResponseEntity.ok(complaintService.create(request));
  }
}
