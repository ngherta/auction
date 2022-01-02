package com.auction.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "auction_action")
//@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Column(name = "gen_date")
    private LocalDateTime genDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AuctionAction that = (AuctionAction) o;
        return bet.equals(that.bet) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bet);
    }
}
