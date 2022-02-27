package com.auction.web.dto.request;

import com.auction.model.enums.ComplaintStatus;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ComplaintAdminRequest {
  private Long adminId;
  private Long complaintId;
  private ComplaintStatus status;
}
