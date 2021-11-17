package com.auction.web.contoller;

import com.auction.dto.request.LoginRequest;
import com.auction.dto.request.SignupRequest;
import com.auction.dto.response.JwtResponse;
import com.auction.dto.response.MessageResponse;
import com.auction.exception.SameCredentialsException;
import com.auction.exception.TokenConfirmationNotFound;
import com.auction.exception.UserAlreadyEnabledException;
import com.auction.service.interfaces.AuthenticationService;
import com.auction.service.interfaces.TokenConfirmationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping()
public class AuthController {
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private TokenConfirmationService confirmationService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        JwtResponse jwtResponse =  authenticationService.authenticateUser(loginRequest);
        return ResponseEntity.ok(jwtResponse);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws SameCredentialsException {
        authenticationService.register(signUpRequest);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/confirm")
    public ResponseEntity<?> confirm(@Param("code") String code) throws UserAlreadyEnabledException, TokenConfirmationNotFound {
        confirmationService.confirm(code);
        return ResponseEntity.ok("Succes confirmation!");
    }
}
