package com.auction.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Getter
@Setter
public class ChatMessage extends AbstractEntity{
  @Column(name = "message")
  private String message;

  @ManyToOne
  @JoinColumn(name = "sender", updatable = false, nullable = false)
  private User sender;

  @Override
  public boolean equals(Object o) {
    return super.equals(o);
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }
}
