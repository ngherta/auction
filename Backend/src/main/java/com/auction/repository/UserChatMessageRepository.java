package com.auction.repository;

import com.auction.model.UserChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserChatMessageRepository extends JpaRepository<UserChatMessage, Long> {
}
