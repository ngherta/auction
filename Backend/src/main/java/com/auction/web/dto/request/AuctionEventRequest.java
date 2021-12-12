package com.auction.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AuctionEventRequest {

    @NotNull
    private String title;
    private String description;
    @NotNull
    private String auctionType;
    @NotNull
    private String auctionStatus;
    @NotNull
    private Double startPrice;
    private Double finishPrice;
    @NotNull
    private Long userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm")
    private LocalDateTime finishDate;
    private Double charityPercent;
    private List<String> images;
}
