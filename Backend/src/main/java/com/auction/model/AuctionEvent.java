package com.auction.model;

import com.auction.model.enums.AuctionStatus;
import com.auction.model.enums.AuctionType;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.thymeleaf.standard.expression.Each;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "auction")
//NGH TODO: remove it
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuctionEvent extends AbstractEntity{
    @Column
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AuctionStatus statusType;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private AuctionType auctionType;

    @Column
    private Double startPrice;

    @Column
    private Double finishPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "genUser_id", nullable = false, updatable = false)
    private User user;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "auction_images",
            joinColumns = @JoinColumn(name = "img_id"),
            inverseJoinColumns = @JoinColumn(name = "auction_id"))
    private List<AuctionEventImg> images = new ArrayList<>();

    @Column
    private Double charityPercent;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime finishDate;

    @Column(name = "gen_date")
    private LocalDateTime genDate = LocalDateTime.now();
}
