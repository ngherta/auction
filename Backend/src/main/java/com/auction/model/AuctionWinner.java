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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "auction_winner")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuctionWinner extends AbstractEntity{
  @OneToOne
  @JoinColumn(name = "auction_id", nullable = false, updatable = false)
  private AuctionEvent auctionEvent;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  private Double price;

  @Column(name = "gen_date", nullable = false)
  private LocalDateTime genDate = LocalDateTime.now();
}
