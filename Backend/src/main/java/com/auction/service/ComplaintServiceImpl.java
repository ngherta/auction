package com.auction.service;

import com.auction.dto.ComplaintDto;
import com.auction.dto.request.ComplaintRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.UserNotFoundException;
import com.auction.model.AuctionEvent;
import com.auction.model.AuctionEventComplaint;
import com.auction.model.User;
import com.auction.repository.AuctionEventComplaintAuditRepository;
import com.auction.repository.AuctionEventComplaintRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.ComplaintService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ComplaintServiceImpl implements ComplaintService {
  private final AuctionEventComplaintRepository complaintRepository;
  private final AuctionEventComplaintAuditRepository complaintAuditRepository;
  private final UserRepository userRepository;
  private final AuctionEventRepository auctionEventRepository;

  @Override
  public ComplaintDto create(ComplaintRequest request) throws AuctionEventNotFoundException, UserNotFoundException {
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

    auctionEventComplaint = complaintRepository.save(auctionEventComplaint);

    ComplaintDto complaintDto = ComplaintDto.from(auctionEventComplaint);
    return complaintDto;
  }
}
