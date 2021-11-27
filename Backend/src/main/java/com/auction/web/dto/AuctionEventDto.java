package com.auction.web.dto;

import com.auction.model.enums.AuctionStatus;
import com.auction.model.enums.AuctionType;
import lombok.*;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AuctionEventDto {

    private Long id;
    private String title;
    private String description;
    private AuctionStatus statusType;
    private AuctionType auctionType;
    private Double startPrice;
    private Double finishPrice;
    private UserDto user;
    private Date startDate;
    private Date finishDate;
    private Date genDate;
    private Double charityPercent;
    private List<String> images;
}
