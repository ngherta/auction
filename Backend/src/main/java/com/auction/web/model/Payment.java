package com.auction.web.model;

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

@Entity
@Table(name = "auction_winner")
@NoArgsConstructor
@Data
public class Payment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @Column(name = "card_number")
  private String cardNumber;

  @Column(name = "expiration_date")
  private String expirationDate;

  @Column(name = "cvv_code")
  private String cvv;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;


  public String getExpirationMonth() {
    return String.valueOf(expirationDate.charAt(0) + expirationDate.charAt(1));
  }

  public String getExpirationYear() {
    return String.valueOf(expirationDate.charAt(3) + expirationDate.charAt(4));
  }

  public void setExpirationDate(String month, String year) {
    this.expirationDate = month + "/" + year;
  }
}
