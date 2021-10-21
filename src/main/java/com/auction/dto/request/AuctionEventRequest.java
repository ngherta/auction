package com.auction.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class AuctionEventRequest {

    private String title;
    private String description;
    private String auctionType;
    private String auctionStatus;
    private Double startPrice;
    private Double finishPrice;
    private Long userId;
    private String startDate;
    private String finishDate;
    private Double charityPercent;

    @JsonCreator
    public AuctionEventRequest(@JsonProperty String title,
                               @JsonProperty String description,
                               @JsonProperty String auctionType,
                               @JsonProperty String auctionStatus,
                               @JsonProperty Double startPrice,
                               @JsonProperty Double finishPrice,
                               @JsonProperty Long userId,
                               @JsonProperty String startDate,
                               @JsonProperty String finishDate,
                               @JsonProperty Double charityPercent) {
        this.title = title;
        this.description = description;
        this.auctionType = auctionType;
        this.auctionStatus = auctionStatus;
        this.startPrice = startPrice;
        this.finishPrice = finishPrice;
        this.userId = userId;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.charityPercent = charityPercent;
    }
}
