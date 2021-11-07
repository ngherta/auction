package com.auction.repository;

import com.auction.web.model.Payment;
import com.auction.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
  List<Payment> findAllByUser(User user);
}
