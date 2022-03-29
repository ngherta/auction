package com.auction.model;

import com.auction.model.enums.PaymentStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payment_order")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@ToString
public class PaymentOrder extends AbstractEntity {
  private Double price;
  private String currency;
  private String method;
  private String intent;
  private String description;
  private String link;
  @Column(name = "paymentId")
  private String paymentId;
  @ManyToOne
  @JoinColumn(name = "user_id", updatable = false, nullable = false)
  private User user;

  @ManyToOne
  @JoinColumn(name = "auction_id", updatable = false, nullable = false)
  private AuctionEvent auctionEvent;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  private PaymentStatus status;
}
