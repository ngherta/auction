package com.auction.model;

import com.auction.model.enums.WithdrawMoneyStatus;
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
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@EqualsAndHashCode(callSuper = true)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class WithdrawMoney extends AbstractEntity {

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @Column
  private Double amount;

  @Column
  @Enumerated(EnumType.STRING)
  private WithdrawMoneyStatus status;

  @Column(name = "gen_date")
  private LocalDateTime genDate;

  @Column
  private String card;

  @Column
  private String name;

  @Column
  private LocalDate date;
}
