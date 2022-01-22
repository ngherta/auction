package com.auction.repository;

import com.auction.model.NotificationMessage;
import com.auction.model.NotificationMessageUser;
import com.auction.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationMessageUserRepository extends JpaRepository<NotificationMessageUser, Long> {
  @Query(nativeQuery = true, value = "" +
          "SELECT * FROM notification_message_user nmu " +
          "LEFT JOIN notification_message nm on nm.id = nmu.notification_message_id " +
          "WHERE nmu.user_id = :userId " +
          "ORDER BY nm.gen_date ASC ")
  List<NotificationMessageUser> findByUserOrderByGenDate(Long userId);

  Optional<NotificationMessageUser> findByUserAndNotificationMessage(User user, NotificationMessage notificationMessage);

  List<NotificationMessageUser> findAllByUserAndAndNotificationMessageIn(User user, List<NotificationMessage> notificationMessages);

  void deleteAllByNotificationMessage(NotificationMessage notificationMessage);
}
