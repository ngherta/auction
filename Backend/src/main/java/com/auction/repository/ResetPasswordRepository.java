package com.auction.repository;

import com.auction.model.ResetPasswordEntity;
import com.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPasswordEntity, Long> {
    Optional<ResetPasswordEntity> findByCode(String code);

    Optional<ResetPasswordEntity> findByUser(User user);

}
