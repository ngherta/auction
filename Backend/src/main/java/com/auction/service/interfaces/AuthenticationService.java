package com.auction.service.interfaces;

import com.auction.dto.request.LoginRequest;
import com.auction.dto.request.SignupRequest;
import com.auction.dto.response.JwtResponse;
import com.auction.exception.SameCredentialsException;

public interface AuthenticationService {
    JwtResponse authenticateUser(LoginRequest loginRequest);

    void register(SignupRequest signUpRequest) throws SameCredentialsException;
}
