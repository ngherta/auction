package com.auction.service.interfaces;

import com.auction.exception.UserNotFoundException;
import com.auction.model.User;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.request.DeleteUserRequest;

import java.util.List;

public interface UserService {

  List<UserDto> getAll();

  void deleteUserById(DeleteUserRequest request);

  UserDto disable(Long userId);

  UserDto enable(Long userId);

}
