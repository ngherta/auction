package com.auction.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
public class AuctionEventRequest {

    @NotNull()
    private String title;
    private String description;
    private String auctionType;
    private String auctionStatus;
    private Double startPrice;
    private Double finishPrice;
    private Long userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm")
    private LocalDateTime startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm")
    private LocalDateTime finishDate;
    private Double charityPercent;
    private List<String> images;
}
