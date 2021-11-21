package com.auction.web.dto;

import com.auction.model.User;
import com.auction.model.enums.AuctionStatus;
import com.auction.model.enums.AuctionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
