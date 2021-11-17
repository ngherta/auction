package com.auction.repository;

import com.auction.web.model.TokenConfirmation;
import com.auction.web.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TokenConfirmationRepository extends JpaRepository<TokenConfirmation, Long> {
  Optional<TokenConfirmation> findByUser(User user);
}
