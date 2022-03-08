package com.auction.repository;

import com.auction.model.NotificationMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationMessageRepository extends JpaRepository<NotificationMessage, Long> {

  @Query(nativeQuery = true, value = "" +
      "WITH user_gen_date AS ( " +
      "SELECT u.gen_date as gen_date from users u where u.id = :userId) " +
      "SELECT * FROM notification_message nm " +
      "WHERE nm.type in :notificationTypes " +
      "AND NOT EXISTS " +
      "(SELECT 1 FROM notification_message_user nmu " +
      "WHERE nmu.user_id = :userId " +
      "AND nmu.notification_message_id = nm.id ) " +
      "AND nm.single_notification = false " +
      "AND nm.gen_date < (select gen_date from user_gen_date LIMIT 1)")
  List<NotificationMessage> findUncreatedNotificationForUser(Long userId, List<String> notificationTypes);


  @Query(nativeQuery = true, value = "" +
      "SELECT * FROM notification_message nm " +
      "WHERE nm.gen_date >= :localDateTime ")
  List<NotificationMessage> findAllOlderThan(LocalDateTime localDateTime);

  List<NotificationMessage> findAllByIdIn(List<Long> ids);
}
