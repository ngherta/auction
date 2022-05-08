package com.auction.repository;

import com.auction.model.User;
import com.auction.model.WithdrawMoney;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WithdrawMoneyRepository extends JpaRepository<WithdrawMoney, Long> {
  List<WithdrawMoney> findAllByUserId(Long userId);

  List<WithdrawMoney> findAllByUser(User user);
}
