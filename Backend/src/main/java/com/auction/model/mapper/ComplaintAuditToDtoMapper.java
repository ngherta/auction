package com.auction.model.mapper;

import com.auction.model.AuctionEventComplaintAudit;
import com.auction.web.dto.ComplaintAuditDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ComplaintAuditToDtoMapper implements Mapper<AuctionEventComplaintAudit, ComplaintAuditDto>{
  private final ComplaintToDtoMapper complaintToDtoMapper;
  private final UserToDtoMapper userToDtoMapper;

  @Override
  public ComplaintAuditDto map(AuctionEventComplaintAudit entity) {
    ComplaintAuditDto complaintAuditDto = new ComplaintAuditDto();
    complaintAuditDto.setAdmin(userToDtoMapper.map(entity.getAdmin()));
    complaintAuditDto.setComplaintDto(complaintToDtoMapper.map(entity.getAuctionEventComplaint()));

    return complaintAuditDto;
  }
}
