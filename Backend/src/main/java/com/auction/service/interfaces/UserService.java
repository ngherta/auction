package com.auction.service.interfaces;

import com.auction.exception.UserNotFoundException;
import com.auction.model.User;
import com.auction.web.dto.request.DeleteUserRequest;

import java.util.List;

public interface UserService {

//    UserDto saveUser(RegistrationRequest request) throws SameCredentialsException;

    List<User> getAll();

    void deleteUserById(DeleteUserRequest request) throws UserNotFoundException;
}
