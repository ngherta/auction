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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "payment_audit")
@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class PaymentAudit extends AbstractEntity{

    @ManyToOne
    @JoinColumn(name = "sender_id", updatable = false, nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "recipient_id", updatable = false, nullable = false)
    private User recipient;

    @Column
    private Double amount;

    @Column
    private String currency;

    @OneToOne
    @JoinColumn(name = "payment_id", updatable = false, nullable = false)
    private PaymentOrder paymentOrder;

    @Column(name = "gen_date")
    private LocalDateTime genDate;

    @Column(name = "commission")
    private Boolean commission;
}
