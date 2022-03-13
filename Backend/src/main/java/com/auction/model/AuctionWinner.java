package com.auction.model;

import com.auction.model.enums.AuctionWinnerStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
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

  @Column(name = "track_number")
  private String trackNumber;

  public boolean hasAddress() {
    return country != null && city != null && address != null;
  }
}
