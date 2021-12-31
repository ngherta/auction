package com.auction.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "token_confirmation")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class TokenConfirmation extends AbstractEntity{

  @OneToOne
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @Column(name = "confirmation")
  private String confirmation;

  @Column(name = "gen_date")
  private LocalDateTime genDate;
}
