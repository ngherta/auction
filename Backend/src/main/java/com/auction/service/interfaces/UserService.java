package com.auction.service.interfaces;

import com.auction.dto.UserDto;
import com.auction.dto.request.DeleteUserRequest;
import com.auction.dto.request.RegistrationRequest;
import com.auction.exception.SameCredentialsException;
import com.auction.exception.UserNotFoundException;
import com.auction.web.model.User;

import java.util.List;

public interface UserService {

//    UserDto saveUser(RegistrationRequest request) throws SameCredentialsException;

    List<UserDto> getAll();

    void deleteUserById(DeleteUserRequest request) throws UserNotFoundException;
}
