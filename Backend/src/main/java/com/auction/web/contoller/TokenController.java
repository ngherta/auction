package com.auction.web.contoller;

import com.auction.service.interfaces.AuthenticationService;
import com.auction.web.dto.response.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/token")
public class TokenController {

  private final AuthenticationService authenticationService;

  @GetMapping("/refresh")
  public ResponseEntity<JwtResponse> refreshToken(@RequestHeader("Authorization") String token) {
    return ResponseEntity.ok(authenticationService.refreshToken(token));
  }
}
