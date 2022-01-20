package com.auction.web.dto.request;

import com.auction.model.enums.AuctionType;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class AuctionEventRequest {

    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private AuctionType auctionType;
    private Double startPrice;
    private Double finishPrice;
    @NotNull
    private Long userId;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm")
    private LocalDateTime startDate;
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm")
    private LocalDateTime finishDate;
    private Double charityPercent;
    private List<String> images;
    private Long categoryIds;
}
