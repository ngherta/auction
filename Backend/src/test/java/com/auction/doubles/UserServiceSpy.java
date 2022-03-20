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

public class UserServiceSpy implements UserService {

  private int invocationOf_get;
  private int invocationOf_deleteById;
  private int invocationOf_disable;
  private int invocationOf_enable;
  private int invocationOf_findById;
  private int invocationOf_getById;
  private int invocationOf_delete;
  private int invocationOf_update;
  private int invocationOf_create;

  public int getInvocationOf_get() {
    return invocationOf_get;
  }

  public int getInvocationOf_deleteById() {
    return invocationOf_deleteById;
  }

  public int getInvocationOf_disable() {
    return invocationOf_disable;
  }

  public int getInvocationOf_enable() {
    return invocationOf_enable;
  }

  public int getInvocationOf_findById() {
    return invocationOf_findById;
  }

  public int getInvocationOf_getById() {
    return invocationOf_getById;
  }

  public int getInvocationOf_delete() {
    return invocationOf_delete;
  }

  public int getInvocationOf_update() {
    return invocationOf_update;
  }

  public int getInvocationOf_create() {
    return invocationOf_create;
  }

  @Override
  public Page<UserDto> get(int page, int perPage) {
    invocationOf_get += 1;
    return null;
  }

  @Override
  public void deleteById(Long userId) {
    invocationOf_deleteById += 1;
  }

  @Override
  public UserDto disable(Long userId) {
    invocationOf_disable += 1;
    return UserFixture.userDto();
  }

  @Override
  public UserDto enable(Long userId) {
    invocationOf_enable += 1;
    return UserFixture.userDto();
  }

  @Override
  public User findById(Long id) {
    invocationOf_findById += 1;
    return UserFixture.user();
  }

  @Override
  public UserDto getById(Long userId) {
    invocationOf_getById += 1;
    return UserFixture.userDto();
  }

  @Override
  public Optional<User> findMainAdmin() {
    return Optional.empty();
  }

  @Override
  public void delete(User user) {
    invocationOf_delete += 1;
  }

  @Override
  public UserDto update(UserUpdateRequest request) {
    invocationOf_update += 1;
    return UserFixture.userDto();
  }

  @Override
  public User create(SignupRequest request) {
    invocationOf_create += 1;
    return UserFixture.user();
  }

  @Override
  public void updatePassword(UpdatePasswordRequest request) {

  }

  @Override
  public void addDefaultAddress(AddDefaultAddressRequest request) {

  }

  @Override
  public void checkTutorial(Long userId) {

  }
}
