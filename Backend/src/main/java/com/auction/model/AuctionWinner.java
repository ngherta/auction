package com.auction.model;

import com.auction.model.enums.AuctionWinnerStatus;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auction_winner")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuctionWinner extends AbstractEntity{
  @OneToOne
  @JoinColumn(name = "auction_id", nullable = false, updatable = false)
  private AuctionEvent auctionEvent;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @Column(name = "price")
  private Double price;

  @OneToOne
  @JoinColumn(name = "payment_order_id")
  private PaymentOrder paymentOrder;

  @Column(name = "gen_date", nullable = false)
  private LocalDateTime genDate;

  @Enumerated(EnumType.STRING)
  private AuctionWinnerStatus status;

  @Column(name = "country")
  private String country;

  @Column(name = "city")
  private String city;

  @Column(name = "address")
  private String address;

  public boolean hasAddress() {
    return country != null && city != null && address != null;
  }
}
