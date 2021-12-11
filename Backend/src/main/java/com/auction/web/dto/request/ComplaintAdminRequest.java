package com.auction.web.dto.request;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ComplaintAdminRequest {
  private Long adminId;
  private Long complaintId;
  private String status;

}
