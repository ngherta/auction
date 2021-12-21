package com.auction.repository;

import com.auction.model.Notification;
import com.auction.model.User;
import com.auction.projection.NotificationProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {


  @Query(nativeQuery = true, value = "" +
          "select n.type as notificationType from notification n " +
          "where n.user_id = :userId " +
          "and n.value = true")
  List<NotificationProjection> findActiveNotificationByUser(@Param("userId") Long userId);

  @Query(nativeQuery = true, value = "" +
          "select * from notification as n " +
          "where n.user_id = :userId " +
          "and n.type = :notificationType")
  Optional<Notification> findByUserAndNotificationType(@Param("userId") Long userId,
                                                       @Param("notificationType") String notificationType);

  void deleteAllByUser(User user);
}
