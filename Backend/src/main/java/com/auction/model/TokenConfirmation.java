package com.auction.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "token_confirmation")
@Data
@NoArgsConstructor
public class TokenConfirmation {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @Column(name = "confirmation")
  private String confirmation;

  @Column
  private Date genDate = new Date();
}