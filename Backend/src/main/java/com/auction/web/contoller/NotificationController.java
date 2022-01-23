package com.auction.web.contoller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("http://localhost:8081")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notification")
public class NotificationController {


}
