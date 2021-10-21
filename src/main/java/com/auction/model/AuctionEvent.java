package com.auction.model;

import com.auction.model.enums.AuctionStatus;
import com.auction.model.enums.AuctionType;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "auction_table")
@NoArgsConstructor
@Data
public class AuctionEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "genUser_id", nullable = false, updatable = false)
    private User user;

    @Column
    private Date startDate;

    @Column
    private Date finishDate;

    @Column
    private Date genDate;
}
