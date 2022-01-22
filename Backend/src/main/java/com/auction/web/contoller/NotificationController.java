package com.auction.web.contoller;

import com.auction.service.interfaces.NotificationMessageService;
import com.auction.service.interfaces.NotificationService;
import com.auction.web.dto.NotificationMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:8081")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {


}
