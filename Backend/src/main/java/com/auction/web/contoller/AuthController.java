package com.auction.web.contoller;

import com.auction.web.dto.request.LoginRequest;
import com.auction.web.dto.request.SignupRequest;
import com.auction.web.dto.response.JwtResponse;
import com.auction.web.dto.response.MessageResponse;
import com.auction.service.interfaces.AuthenticationService;
import com.auction.service.interfaces.TokenConfirmationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;
    private final TokenConfirmationService confirmationService;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse =  authenticationService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws MessagingException, UnsupportedEncodingException {
        authenticationService.register(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@Param("code") String code) {
        confirmationService.confirm(code);
        return ResponseEntity.ok(new MessageResponse("Success confirmation!"));
    }
}
