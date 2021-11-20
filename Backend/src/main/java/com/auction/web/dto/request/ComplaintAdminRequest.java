package com.auction.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class ComplaintAdminRequest {
  private Long adminId;
  private Long complaintId;
  private String status;

  @JsonCreator
  public ComplaintAdminRequest(@JsonProperty Long adminId,
                               @JsonProperty Long complaintId,
                               @JsonProperty String status) {
    this.adminId = adminId;
    this.complaintId = complaintId;
    this.status = status;
  }
}
