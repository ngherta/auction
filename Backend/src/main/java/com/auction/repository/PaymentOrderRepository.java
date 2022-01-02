package com.auction.repository;

import com.auction.model.AuctionEvent;
import com.auction.model.PaymentOrder;
import com.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
  Optional<PaymentOrder> findByAuctionEvent(AuctionEvent auctionEvent);

  Optional<PaymentOrder> findByPaymentId(String paymentId);

  List<PaymentOrder> findByUser(User user);
}
