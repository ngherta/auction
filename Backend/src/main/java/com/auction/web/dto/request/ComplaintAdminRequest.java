package com.auction.web.dto.request;

import com.auction.model.enums.ComplaintStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;


@Data
@NoArgsConstructor
public class ComplaintAdminRequest {
  @NotNull
  private Long adminId;
  @NotNull
  private Long complaintId;
  private ComplaintStatus status;

}
