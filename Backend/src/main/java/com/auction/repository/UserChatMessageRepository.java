package com.auction.repository;

import com.auction.model.UserChat;
import com.auction.model.UserChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserChatMessageRepository extends JpaRepository<UserChatMessage, Long> {
  List<UserChatMessage> findByUserChat(UserChat chat);
}
