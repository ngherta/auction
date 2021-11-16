package com.auction.dto;

import com.auction.web.model.AuctionCharity;
import com.auction.web.model.AuctionEvent;
import com.auction.web.model.User;
import com.auction.web.model.enums.AuctionStatus;
import com.auction.web.model.enums.AuctionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
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

    private User user;

    private Date startDate;

    private Date finishDate;

    private Date genDate;

    private Double charityPercent;

    private List<String> images;

    public static AuctionEventDto from(AuctionEvent auctionEvent) {
        AuctionEventDto auctionEventDto = AuctionEventDto.builder()
                .id(auctionEvent.getId())
                .title(auctionEvent.getTitle())
                .description(auctionEvent.getDescription())
                .statusType(auctionEvent.getStatusType())
                .auctionType(auctionEvent.getAuctionType())
                .startPrice(auctionEvent.getStartPrice())
                .finishPrice(auctionEvent.getFinishPrice())
                .user(auctionEvent.getUser())
                .startDate(auctionEvent.getStartDate())
                .finishDate(auctionEvent.getFinishDate())
                .genDate(auctionEvent.getGenDate())
                .build();
        List<String> images = new ArrayList<>();
        auctionEvent.getImages().stream().map(e -> images.add(e.getUrl()));
        auctionEventDto.setImages(images);

        return auctionEventDto;
    }

    public static AuctionEventDto from(AuctionEvent auctionEvent,
                                       AuctionCharity auctionCharity) {
        AuctionEventDto auctionEventDto = from(auctionEvent);
        if (auctionCharity.getPercent() != null) {
            auctionEventDto.setCharityPercent(auctionCharity.getPercent());
        }
        return auctionEventDto;
    }
}
