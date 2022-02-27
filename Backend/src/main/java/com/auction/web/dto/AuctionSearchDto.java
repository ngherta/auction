package com.auction.web.dto;

import com.auction.model.enums.AuctionStatus;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuctionSearchDto {
    private Long id;
    private String title;
    private AuctionStatus status;
    private String startDate;
    private String finishDate;
}
