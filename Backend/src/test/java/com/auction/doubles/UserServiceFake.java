package com.auction.doubles;

import com.auction.model.User;
import com.auction.model.fixture.UserFixture;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.request.AddDefaultAddressRequest;
import com.auction.web.dto.request.SignupRequest;
import com.auction.web.dto.request.UpdatePasswordRequest;
import com.auction.web.dto.request.UserUpdateRequest;
import org.springframework.data.domain.Page;

import java.util.Optional;

public class UserServiceFake implements UserService {
  @Override
  public Page<UserDto> get(int page, int perPage) {
    return null;
  }

  @Override
  public void deleteById(Long userId) {

  }

  @Override
  public UserDto disable(Long userId) {
    return UserFixture.userDto();
  }

  @Override
  public UserDto enable(Long userId) {
    return UserFixture.userDto();
  }

  @Override
  public User findById(Long id) {
    return UserFixture.user();
  }

  @Override
  public UserDto getById(Long userId) {
    return UserFixture.userDto();
  }

  @Override
  public Optional<User> findMainAdmin() {
    return Optional.empty();
  }

  @Override
  public void delete(User user) {

  }

  @Override
  public UserDto update(UserUpdateRequest request) {
    return UserFixture.userDto();
  }

  @Override
  public User create(SignupRequest request) {
    return UserFixture.user();
  }

  @Override
  public void updatePassword(UpdatePasswordRequest request) {

  }

  @Override
  public void addDefaultAddress(AddDefaultAddressRequest request) {

  }
}
