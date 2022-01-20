package com.auction.repository;

import com.auction.model.NotificationMessage;
import com.auction.model.NotificationMessageUser;
import com.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationMessageUserRepository extends JpaRepository<NotificationMessageUser, Long> {
  List<NotificationMessageUser> findByUser(User user);

  void deleteAllByNotificationMessage(NotificationMessage notificationMessage);
}
