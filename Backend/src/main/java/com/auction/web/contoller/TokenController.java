package com.auction.web.contoller;

import com.auction.service.interfaces.AuthenticationService;
import com.auction.web.dto.response.JwtResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@CrossOrigin("http://34.140.181.128:8082")
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
