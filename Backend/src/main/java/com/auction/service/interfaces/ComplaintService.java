package com.auction.service.interfaces;

import com.auction.dto.ComplaintDto;
import com.auction.dto.request.ComplaintRequest;
import com.auction.exception.AuctionEventNotFoundException;
import com.auction.exception.UserNotFoundException;

public interface ComplaintService {
  ComplaintDto create(ComplaintRequest request) throws AuctionEventNotFoundException, UserNotFoundException;
}
