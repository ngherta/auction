package com.auction.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
public class AuctionEventRequest {

    private String title;
    private String description;
    private String auctionType;
    private String auctionStatus;
    private Double startPrice;
    private Double finishPrice;
    private Long userId;
    private Date startDate;
    private Date finishDate;
    private Double charityPercent;

    @JsonCreator
    public AuctionEventRequest(@JsonProperty String title,
                               @JsonProperty String description,
                               @JsonProperty String auctionType,
                               @JsonProperty String auctionStatus,
                               @JsonProperty Double startPrice,
                               @JsonProperty Double finishPrice,
                               @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm")
                               Date startDate,
                               @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm")
                               Date finishDate,
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
