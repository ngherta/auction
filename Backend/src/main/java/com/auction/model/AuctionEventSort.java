package com.auction.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "auction_sort")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuctionEventSort extends AbstractEntity{
  @OneToOne
  @JoinColumn(name = "auction_id", nullable = false, updatable = false)
  private AuctionEvent auctionEvent;

  @Column(name = "sort_rating")
  private Long sortRating;
}
