package com.auction.service.interfaces;

import com.auction.model.User;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.request.DeleteUserRequest;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

  Page<UserDto> get(int page, int perPage);

  void deleteUserById(DeleteUserRequest request);

  UserDto disable(Long userId);

  UserDto enable(Long userId);

  User findById(Long id);
}
