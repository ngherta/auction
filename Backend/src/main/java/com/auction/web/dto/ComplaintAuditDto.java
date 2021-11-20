package com.auction.web.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintAuditDto {
  private ComplaintDto complaintDto;
  private UserDto admin;
}
