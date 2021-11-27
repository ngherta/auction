package com.auction.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "auction_action")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionAction extends AbstractEntity {
    @ManyToOne
    @JoinColumn(name = "auction_id", nullable = false, updatable = false)
    private AuctionEvent auctionEvent;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @Column(name = "bet")
    private Double bet;

    @Column(name = "genDate")
    private LocalDateTime genDate = LocalDateTime.now();
}
