package com.auction.service.interfaces;

import com.auction.model.AuctionEventComplaint;
import com.auction.model.AuctionEventComplaintAudit;
import com.auction.web.dto.ComplaintDto;
import com.auction.web.dto.request.ComplaintAdminRequest;
import com.auction.web.dto.request.ComplaintRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.UserNotFoundException;

import java.util.List;

public interface ComplaintService {
  AuctionEventComplaint create(ComplaintRequest request) throws AuctionEventNotFoundException, UserNotFoundException;

  List<AuctionEventComplaint> getAll();

  AuctionEventComplaintAudit satisfyComplaint(ComplaintAdminRequest request) throws UserNotFoundException;
}
