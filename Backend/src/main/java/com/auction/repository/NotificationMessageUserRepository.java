package com.auction.repository;

import com.auction.model.NotificationMessageUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationMessageUserRepository extends JpaRepository<NotificationMessageUser, Long> {
}
