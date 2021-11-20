package com.auction.model;

import com.auction.model.enums.ComplaintStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "auction_complaint")
@NoArgsConstructor
@Data
public class AuctionEventComplaint {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @JoinColumn(name = "auction_id", nullable = false, updatable = false)
  private AuctionEvent auctionEvent;

  @ManyToOne
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @JoinColumn(name = "user_id", nullable = false, updatable = false)
  private User user;

  @Column(name = "message")
  @Lob
  private String message;

  @Column(name = "complaint_status")
  @Enumerated(EnumType.STRING)
  private ComplaintStatus status;

  @Column(name = "gen_date")
  private Date genDate;

}
