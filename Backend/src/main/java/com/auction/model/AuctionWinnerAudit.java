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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "auction_winner_audit")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuctionWinnerAudit extends AbstractEntity{

  @ManyToOne
  @JoinColumn(name = "auction_winner_id", nullable = false, updatable = false)
  private AuctionWinner auctionWinner;

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

  @Column(nullable = false, updatable = false)
  private LocalDateTime created;
}
