package com.auction.web.contoller;

import com.auction.config.jwt.JwtProvider;
import com.auction.dto.request.AuthRequest;
import com.auction.dto.request.RegistrationRequest;
import com.auction.dto.response.AuthResponse;
import com.auction.exception.SameCredentialsException;
import com.auction.web.model.User;
import com.auction.service.interfaces.AuthenticationService;
import com.auction.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/register")
//    @Valid
    public ResponseEntity registerUser(@RequestBody RegistrationRequest registrationRequest) throws SameCredentialsException {
        return ResponseEntity.ok(userService.saveUser(registrationRequest));
    }

    @PostMapping("/auth")
    public AuthResponse auth(@RequestBody AuthRequest request) throws Exception {
        User user = authenticationService.authenticate(request);

        if (user == null) {
            throw new UsernameNotFoundException("User with login" + request.getLogin() + "doesn't exist");
        }

        String token = jwtProvider.generateToken(user.getLogin());
        return new AuthResponse(token);
    }
}
