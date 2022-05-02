package com.auction.model.mapper;

import com.auction.model.AuctionEventComplaint;
import com.auction.model.AuctionEventComplaintAudit;
import com.auction.model.User;
import com.auction.web.dto.ComplaintAuditDto;
import com.auction.web.dto.ComplaintDto;
import com.auction.web.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
class ComplaintAuditToDtoMapper implements Mapper<AuctionEventComplaintAudit, ComplaintAuditDto>{

  private final Mapper<AuctionEventComplaint, ComplaintDto> complaintToDtoMapper;
  private final Mapper<User, UserDto> userToDtoMapper;

  @Override
  public ComplaintAuditDto map(AuctionEventComplaintAudit entity) {
    ComplaintAuditDto complaintAuditDto = new ComplaintAuditDto();
    complaintAuditDto.setAdmin(userToDtoMapper.map(entity.getAdmin()));
    complaintAuditDto.setComplaintDto(complaintToDtoMapper.map(entity.getAuctionEventComplaint()));

    return complaintAuditDto;
  }
}
