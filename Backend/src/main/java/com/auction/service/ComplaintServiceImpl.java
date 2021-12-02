package com.auction.service;

import com.auction.exception.UserNotFoundException;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionEventComplaint;
import com.auction.model.AuctionEventComplaintAudit;
import com.auction.model.User;
import com.auction.model.enums.ComplaintStatus;
import com.auction.model.mapper.ComplaintAuditToDtoMapper;
import com.auction.model.mapper.ComplaintToDtoMapper;
import com.auction.repository.AuctionEventComplaintAuditRepository;
import com.auction.repository.AuctionEventComplaintRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.UserRepository;
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

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComplaintServiceImpl implements ComplaintService {
  private final AuctionEventComplaintRepository complaintRepository;
  private final AuctionEventComplaintAuditRepository complaintAuditRepository;
  private final UserRepository userRepository;
  private final UserService userService;
  private final AuctionEventRepository auctionEventRepository;
  private final AuctionEventService auctionEventService;
  private final ComplaintToDtoMapper complaintToDtoMapper;
  private final ComplaintAuditToDtoMapper complaintAuditToDtoMapper;

  @Override
  @Transactional
  public ComplaintDto create(ComplaintRequest request) {
    AuctionEventComplaint auctionEventComplaint = new AuctionEventComplaint();

    AuctionEvent auctionEvent = auctionEventService.findById(request.getUserId());

    User user = userService.findById(request.getUserId());

    auctionEventComplaint.setUser(user);
    auctionEventComplaint.setAuctionEvent(auctionEvent);
    auctionEventComplaint.setMessage(request.getMessage());
    auctionEventComplaint.setStatus(ComplaintStatus.WAITING);

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
    AuctionEventComplaintAudit complaintAudit = new AuctionEventComplaintAudit();
    AuctionEvent auctionEvent = new AuctionEvent();
    //exception
    if (request.getStatus().equals(ComplaintStatus.REJECTED)) {
      complaint.setStatus(ComplaintStatus.REJECTED);
      complaintAudit.setComplaintStatus(ComplaintStatus.REJECTED);
    }
    else if(request.getStatus().equals(ComplaintStatus.SATISFIED)) {
      complaint.setStatus(ComplaintStatus.SATISFIED);
      auctionEvent = auctionEventService.blockAuctionEvent(auctionEvent);
      complaint.setAuctionEvent(auctionEvent);
      complaintAudit.setComplaintStatus(ComplaintStatus.SATISFIED);
    }

    User admin = userService.findById(request.getAdminId());

    complaintAudit.setAdmin(admin);
    complaintAudit.setAuctionEventComplaint(complaint);
    complaintAudit = complaintAuditRepository.save(complaintAudit);

    return complaintAuditToDtoMapper.map(complaintAudit);
  }
}
