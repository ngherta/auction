package com.auction.web.contoller;

import com.auction.service.interfaces.NotificationService;
import com.auction.web.dto.SettingsDto;
import com.auction.web.dto.request.NotificationUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin("http://34.140.181.128:8082")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/settings")
public class SettingsController {
  private final NotificationService notificationService;

  @GetMapping("/{userId}")
  public ResponseEntity<SettingsDto> getAll(@PathVariable Long userId) {
    SettingsDto dto = SettingsDto
            .builder()
            .notifications(notificationService.getAllByUser(userId))
            .build();
    return ResponseEntity.ok(dto);
  }

  @PutMapping("/notification")
  public ResponseEntity<Void> updateNotification(@RequestBody NotificationUpdateRequest request) {
    notificationService.update(request);
    return ResponseEntity.ok().build();
  }

}
