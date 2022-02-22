package com.auction.repository;

import com.auction.model.PaymentAudit;
import com.auction.model.User;
import com.auction.projection.CommissionPerMouthProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentAuditRepository extends JpaRepository<PaymentAudit, Long> {

    Page<PaymentAudit> findAllByRecipient(User recipient, Pageable pageable);

    @Query(nativeQuery = true, value = "" +
            "SELECT to_char(pa.gen_date, 'YYYY-Month')          AS date, " +
            "       to_char(pa.gen_date, 'Month')                AS month, " +
            "       to_char(pa.gen_date, 'YYYY')                AS year, " +
            "       sum(pa.amount)                              AS amount, " +
            "       CAST(to_char(pa.gen_date, 'mm') AS integer) AS index " +
            "FROM payment_audit pa " +
            "WHERE pa.gen_date > current_date - INTERVAL '12 months' " +
            "  AND to_char(pa.gen_date, 'YYYY-Month') != to_char(current_date - INTERVAL '12 months', 'YYYY-Month') " +
            "GROUP BY date, year, index, month " +
            "ORDER BY year, index ")
    List<CommissionPerMouthProjection> getCommissionPerMouth();


}
