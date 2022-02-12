package com.auction.service.interfaces;

import com.auction.model.User;
import com.auction.web.dto.ComplaintAuditDto;
import com.auction.web.dto.ComplaintDto;
import com.auction.web.dto.request.ComplaintAdminRequest;
import com.auction.web.dto.request.ComplaintRequest;
import org.springframework.data.domain.Page;

public interface ComplaintService {
  ComplaintDto create(ComplaintRequest request);

  Page<ComplaintDto> getAll(int page, int perPage);

  ComplaintAuditDto satisfyComplaint(ComplaintAdminRequest request);

  void deleteAllByUser(User user);
}
