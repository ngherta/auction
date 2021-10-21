package com.auction.service.interfaces;

import com.auction.dto.UserDto;
import com.auction.dto.request.RegistrationRequest;
import com.auction.exception.SameCredentialsException;
import com.auction.model.User;

import java.util.List;

public interface UserService {

    UserDto saveUser(RegistrationRequest request) throws SameCredentialsException;

    User findByLogin(String login);

    User findByLoginAndPassword(String login, String password) throws Exception;

    List<UserDto> getAll();
}
