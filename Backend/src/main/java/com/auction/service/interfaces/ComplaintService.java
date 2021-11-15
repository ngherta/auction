package com.auction.service.interfaces;

import com.auction.dto.ComplaintDto;
import com.auction.dto.request.ComplaintAdminRequest;
import com.auction.dto.request.ComplaintRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.UserNotFoundException;

import java.util.List;

public interface ComplaintService {
  ComplaintDto create(ComplaintRequest request) throws AuctionEventNotFoundException, UserNotFoundException;

  List<ComplaintDto> getAll();

  ComplaintDto blockAuction(ComplaintAdminRequest request) throws UserNotFoundException;


}
