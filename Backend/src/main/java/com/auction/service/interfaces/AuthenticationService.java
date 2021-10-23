package com.auction.service.interfaces;

import com.auction.dto.UserDto;
import com.auction.dto.request.AuthRequest;
import com.auction.model.User;

import javax.mail.AuthenticationFailedException;

public interface AuthenticationService {
    User authenticate (AuthRequest request) throws AuthenticationFailedException;
}
