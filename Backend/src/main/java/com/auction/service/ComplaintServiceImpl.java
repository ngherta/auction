package com.auction.service;

import com.auction.event.notification.ComplaintNotificationEvent;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionEventComplaint;
import com.auction.model.AuctionEventComplaintAudit;
import com.auction.model.User;
import com.auction.model.enums.ComplaintStatus;
import com.auction.model.mapper.Mapper;
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
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
class ComplaintServiceImpl implements ComplaintService {
  private final AuctionEventComplaintRepository complaintRepository;
  private final AuctionEventComplaintAuditRepository complaintAuditRepository;
  private final UserService userService;
  private final ApplicationEventPublisher publisher;
  private final AuctionEventService auctionEventService;
  private final Mapper<AuctionEventComplaint, ComplaintDto> complaintToDtoMapper;
  private final Mapper<AuctionEventComplaintAudit, ComplaintAuditDto> complaintAuditToDtoMapper;

  @Override
  @Transactional
  public ComplaintDto create(ComplaintRequest request) {
    AuctionEvent auctionEvent = auctionEventService.findById(request.getAuctionEventId());

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
  public Page<ComplaintDto> getAll(int page, int perPage) {
    Pageable pageable = PageRequest.of(page - 1, perPage);
    return complaintRepository
            .findAll(pageable)
            .map(complaintToDtoMapper::map);
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
    else if (request.getStatus() == ComplaintStatus.SATISFIED) {
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

    publisher.publishEvent(new ComplaintNotificationEvent(audit));
    return complaintAuditToDtoMapper.map(audit);
  }

  @Override
  public void deleteAllByUser(User user) {
    complaintAuditRepository.deleteAllByAdmin(user);
    complaintRepository.deleteAllByUser(user);
  }
}
