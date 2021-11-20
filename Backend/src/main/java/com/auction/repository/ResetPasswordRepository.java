package com.auction.repository;

import com.auction.web.model.ResetPasswordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPasswordEntity, Long> {
    ResetPasswordEntity findByCode(String code);

}
