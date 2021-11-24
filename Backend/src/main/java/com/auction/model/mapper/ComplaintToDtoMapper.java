package com.auction.model.mapper;

import com.auction.model.AuctionEventComplaint;
import com.auction.web.dto.ComplaintDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ComplaintToDtoMapper implements Mapper<AuctionEventComplaint, ComplaintDto>{

  private final AuctionEventToDtoMapper auctionEventToDtoMapper;
  private final UserToDtoMapper userToDtoMapper;

  @Override
  public ComplaintDto map(AuctionEventComplaint entity) {
    ComplaintDto complaintDto = ComplaintDto.builder()
            .id(entity.getId())
            .user(userToDtoMapper.map(entity.getUser()))
            .auctionEvent(auctionEventToDtoMapper.map(entity.getAuctionEvent()))
            .genDate(entity.getGenDate())
            .message(entity.getMessage())
            .status(entity.getStatus())
            .build();

    return complaintDto;
  }

}
