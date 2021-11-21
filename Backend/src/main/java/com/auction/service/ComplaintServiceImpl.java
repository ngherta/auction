package com.auction.service;

import com.auction.web.dto.ComplaintDto;
import com.auction.web.dto.request.ComplaintAdminRequest;
import com.auction.web.dto.request.ComplaintRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.UserNotFoundException;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionEventComplaint;
import com.auction.model.AuctionEventComplaintAudit;
import com.auction.model.User;
import com.auction.model.enums.ComplaintStatus;
import com.auction.repository.AuctionEventComplaintAuditRepository;
import com.auction.repository.AuctionEventComplaintRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.ComplaintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComplaintServiceImpl implements ComplaintService {
  private final AuctionEventComplaintRepository complaintRepository;
  private final AuctionEventComplaintAuditRepository complaintAuditRepository;
  private final UserRepository userRepository;
  private final AuctionEventRepository auctionEventRepository;
  private final AuctionEventService auctionEventService;

  @Override
  @Transactional
  public AuctionEventComplaint create(ComplaintRequest request) throws AuctionEventNotFoundException, UserNotFoundException {
    AuctionEventComplaint auctionEventComplaint = new AuctionEventComplaint();

    Optional<AuctionEvent> auctionEvent = auctionEventRepository.findById(request.getAuctionEventId());
    if (auctionEvent.isEmpty()) {
      throw new AuctionEventNotFoundException("AuctionEvent[" + request.getAuctionEventId() + "] doesn't exist");
    }
    Optional<User> user = userRepository.findById(request.getUserId());
    if (user.isEmpty()) {
      throw new UserNotFoundException("User[" + request.getUserId() + "] doesn't exist");
    }

    auctionEventComplaint.setUser(user.get());
    auctionEventComplaint.setAuctionEvent(auctionEvent.get());
    auctionEventComplaint.setGenDate(new Date());
    auctionEventComplaint.setMessage(request.getMessage());
    auctionEventComplaint.setStatus(ComplaintStatus.WAITING);

    auctionEventComplaint = complaintRepository.save(auctionEventComplaint);

    return auctionEventComplaint;
  }

  @Override
  public List<AuctionEventComplaint> getAll() {
    List<AuctionEventComplaint> list = complaintRepository.findAll();
    return list;
  }

  @Override
  @Transactional
  public AuctionEventComplaintAudit satisfyComplaint(ComplaintAdminRequest request) throws UserNotFoundException {
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

    Optional<User> admin = userRepository.findById(request.getAdminId());
    if (admin.isEmpty()) {
      throw new UserNotFoundException("User[" + request.getAdminId() + "] doesn't exist");
    }

    complaintAudit.setAdmin(admin.get());
    complaintAudit.setGenDate(new Date());
    complaintAudit.setAuctionEventComplaint(complaint);
    complaintAudit = complaintAuditRepository.save(complaintAudit);

    return complaintAudit;
  }
}
