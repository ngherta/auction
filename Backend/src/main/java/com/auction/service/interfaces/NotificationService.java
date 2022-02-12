package com.auction.service.interfaces;

import com.auction.model.User;
import com.auction.projection.NotificationProjection;
import com.auction.web.dto.NotificationDto;
import com.auction.web.dto.request.NotificationUpdateRequest;

import java.util.List;

public interface NotificationService {
  void createDefaultNotificationsForUser(User user);

  List<NotificationProjection> getNotificationTypeByUser(User user);

  void deleteByUser(User user);

  List<NotificationDto> getAllByUser(Long userId);

  List<NotificationDto> getAllByUser(User user);

  void update(NotificationUpdateRequest request);
}
