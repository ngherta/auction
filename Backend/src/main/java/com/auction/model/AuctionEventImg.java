package com.auction.model;

import liquibase.pro.packaged.A;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "auction_img")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuctionEventImg extends AbstractEntity{
  @Column(name = "url", nullable = false)
  private String url;
}
