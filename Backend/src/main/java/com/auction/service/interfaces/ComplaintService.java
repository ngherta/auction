package com.auction.service.interfaces;

import com.auction.model.AuctionEventComplaint;
import com.auction.model.AuctionEventComplaintAudit;
import com.auction.web.dto.ComplaintAuditDto;
import com.auction.web.dto.ComplaintDto;
import com.auction.web.dto.request.ComplaintAdminRequest;
import com.auction.web.dto.request.ComplaintRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.UserNotFoundException;

import java.util.List;

public interface ComplaintService {
  ComplaintDto create(ComplaintRequest request);

  List<ComplaintDto> getAll();

  ComplaintAuditDto satisfyComplaint(ComplaintAdminRequest request);
}
