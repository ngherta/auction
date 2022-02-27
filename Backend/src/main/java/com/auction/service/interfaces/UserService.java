package com.auction.service.interfaces;

import com.auction.model.User;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.request.SignupRequest;
import com.auction.web.dto.request.UpdatePasswordRequest;
import com.auction.web.dto.request.UserUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {

  Page<UserDto> get(int page, int perPage);

  void deleteById(Long userId);

  UserDto disable(Long userId);

  UserDto enable(Long userId);

  User findById(Long id);

  UserDto getById(Long userId);

  Optional<User> findMainAdmin();

  void delete(User user);

  UserDto update(UserUpdateRequest request);

  User create(SignupRequest request);

  void updatePassword(UpdatePasswordRequest request);
}
