package com.auction.service;

import com.auction.model.AuctionEvent;
import com.auction.model.AuctionEventComplaint;
import com.auction.model.AuctionEventComplaintAudit;
import com.auction.model.User;
import com.auction.model.enums.ComplaintStatus;
import com.auction.model.mapper.ComplaintAuditToDtoMapper;
import com.auction.model.mapper.ComplaintToDtoMapper;
import com.auction.repository.AuctionEventComplaintAuditRepository;
import com.auction.repository.AuctionEventComplaintRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.ComplaintService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.ComplaintAuditDto;
import com.auction.web.dto.ComplaintDto;
import com.auction.web.dto.request.ComplaintAdminRequest;
import com.auction.web.dto.request.ComplaintRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
class ComplaintServiceImpl implements ComplaintService {
  private final AuctionEventComplaintRepository complaintRepository;
  private final AuctionEventComplaintAuditRepository complaintAuditRepository;
  private final UserService userService;
  private final AuctionEventService auctionEventService;
  private final ComplaintToDtoMapper complaintToDtoMapper;
  private final ComplaintAuditToDtoMapper complaintAuditToDtoMapper;

  @Override
  @Transactional
  public ComplaintDto create(ComplaintRequest request) {
    AuctionEvent auctionEvent = auctionEventService.findById(request.getUserId());

    User user = userService.findById(request.getUserId());

    AuctionEventComplaint auctionEventComplaint = AuctionEventComplaint.builder()
            .user(user)
            .auctionEvent(auctionEvent)
            .message(request.getMessage())
            .status(ComplaintStatus.WAITING)
            .genDate(LocalDateTime.now())
            .build();

    auctionEventComplaint = complaintRepository.save(auctionEventComplaint);

    return complaintToDtoMapper.map(auctionEventComplaint);
  }

  @Override
  @Transactional(readOnly = true)
  public List<ComplaintDto> getAll() {
    List<AuctionEventComplaint> list = complaintRepository.findAll();
    return complaintToDtoMapper.mapList(list);
  }

  @Override
  @Transactional
  public ComplaintAuditDto satisfyComplaint(ComplaintAdminRequest request) {
    AuctionEventComplaint complaint = complaintRepository.getById(request.getComplaintId());
    ComplaintStatus complaintStatus = complaint.getStatus();
    AuctionEvent auctionEvent = complaint.getAuctionEvent();

    if (request.getStatus() == ComplaintStatus.REJECTED) {
      complaint.setStatus(ComplaintStatus.REJECTED);
      complaintStatus = ComplaintStatus.REJECTED;
    }
    else if(request.getStatus() == ComplaintStatus.SATISFIED) {
      complaint.setStatus(ComplaintStatus.SATISFIED);
      auctionEvent = auctionEventService.blockAuctionEvent(auctionEvent);
      complaint.setAuctionEvent(auctionEvent);
      complaintStatus = ComplaintStatus.SATISFIED;
    }

    User admin = userService.findById(request.getAdminId());

    AuctionEventComplaintAudit audit = AuctionEventComplaintAudit.builder()
            .admin(admin)
            .complaintStatus(complaintStatus)
            .auctionEventComplaint(complaint)
            .genDate(LocalDateTime.now())
            .build();

    audit = complaintAuditRepository.save(audit);

    return complaintAuditToDtoMapper.map(audit);
  }
}
