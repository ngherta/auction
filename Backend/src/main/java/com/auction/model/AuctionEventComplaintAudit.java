package com.auction.model;

import com.auction.model.enums.ComplaintStatus;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "auction_complaint_audit")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AuctionEventComplaintAudit extends AbstractEntity{
  @ManyToOne
  @JoinColumn(name = "auction_complaint_id", nullable = false, updatable = false)
  private AuctionEventComplaint auctionEventComplaint;

  @ManyToOne
  @JoinColumn(name = "user_admin_id", nullable = false, updatable = false)
  private User admin;

  @Column(name = "status")
  private ComplaintStatus complaintStatus;

  @Column(name = "gen_date")
  private LocalDateTime genDate;

}
