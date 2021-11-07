package com.auction.web.model;

import com.auction.web.model.enums.ComplaintStatus;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "auction_complaint_audit")
@NoArgsConstructor
@Data
public class AuctionEventComplaintAudit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @JoinColumn(name = "auction_complaint_id", nullable = false, updatable = false)
  private AuctionEventComplaint auctionEventComplaint;

  @ManyToOne
  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @JoinColumn(name = "user_admin_id", nullable = false, updatable = false)
  private User admin;

  @Column(name = "status")
  private ComplaintStatus complaintStatus;

  @Column(name = "gen_date")
  private Date genDate;

}
