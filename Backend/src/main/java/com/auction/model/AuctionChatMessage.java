package com.auction.model;

import lombok.AccessLevel;
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

@Entity
@Table(name = "chat_auction_message")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuctionChatMessage extends AbstractEntity{
  @Column(name = "message")
  private String message;

  @ManyToOne
  @JoinColumn(name = "sender", updatable = false, nullable = false)
  private User sender;

  @ManyToOne
  @JoinColumn(name = "chat_room", updatable = false, nullable = false)
  private AuctionChat auctionChat;
}
