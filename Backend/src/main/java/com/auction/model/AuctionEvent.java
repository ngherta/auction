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

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "genUser_id", nullable = false, updatable = false)
    private User user;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> images;

    @Column
    private Double charityPercent;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime finishDate;

    @Column(name = "gen_date")
    private LocalDateTime genDate;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(	name = "auction_category",
            joinColumns = @JoinColumn(name = "auction_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_category_id"))
    private Set<SubCategory> categories = new HashSet<>();
}
