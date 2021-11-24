package com.auction.model;

import com.auction.model.enums.AuctionStatus;
import com.auction.model.enums.AuctionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "auction")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionEvent extends AbstractEntity{
    @Column
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
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

    @ManyToOne
    @JoinColumn(name = "genUser_id", nullable = false, updatable = false)
    private User user;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "auction_images",
            joinColumns = @JoinColumn(name = "img_id"),
            inverseJoinColumns = @JoinColumn(name = "auction_id"))
    private List<AuctionEventImg> images = new ArrayList<>();

    @Column
    private Double charityPercent;

    @Column
    private Date startDate;

    @Column
    private Date finishDate;

    @Column(name = "gen_date")
    private Date genDate;
}
