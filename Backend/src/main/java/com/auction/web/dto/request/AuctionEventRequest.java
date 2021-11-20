package com.auction.web.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

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
    private Set<String> images;

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
                               @JsonProperty Double charityPercent,
                               @JsonProperty Set<String> images) {
        this.title = title;
        this.description = description;
        this.auctionType = auctionType;
        this.auctionStatus = auctionStatus;
        this.startPrice = startPrice;
        this.finishPrice = finishPrice;
        this.images = images;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.charityPercent = charityPercent;
    }
}
