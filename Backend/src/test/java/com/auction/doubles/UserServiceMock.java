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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class UserServiceMock implements UserService {

  private List<User> fakeUserRepository;
  private List<UserDto> fakeUserDtoRepository;

  public UserServiceMock(List<User> fakeUserRepository,
                         List<UserDto> fakeUserDTORepository) {
    this.fakeUserRepository = fakeUserRepository;
    this.fakeUserDtoRepository = fakeUserDTORepository;
  }

  @Override
  public Page<UserDto> get(int page, int perPage) {
    return null;
  }

  @Override
  public void deleteById(Long userId) {
    fakeUserRepository = fakeUserRepository
            .stream()
            .filter(u -> u.getId() == userId)
            .collect(Collectors.toList());
  }

  @Override
  public UserDto disable(Long userId) {
    UserDto dto = fakeUserDtoRepository
            .stream()
            .filter(u -> u.getId() == userId)
            .findFirst().get();
    dto.setEnabled(false);
    return dto;
  }

  @Override
  public UserDto enable(Long userId) {
    UserDto dto = fakeUserDtoRepository
            .stream()
            .filter(u -> u.getId() == userId)
            .findFirst().orElseThrow();
    dto.setEnabled(true);
    return dto;
  }

  @Override
  public User findById(Long id) {
    return fakeUserRepository
            .stream()
            .filter(e -> e.getId() == id)
            .findFirst()
            .orElseThrow();
  }

  @Override
  public UserDto getById(Long userId) {
    return fakeUserDtoRepository
            .stream()
            .filter(e -> e.getId() == userId)
            .findFirst()
            .orElseThrow();
  }

  @Override
  public Optional<User> findMainAdmin() {
    return Optional.empty();
  }

  @Override
  public void delete(User user) {
    fakeUserRepository = fakeUserRepository
            .stream()
            .filter(u -> u.getId() == user.getId())
            .collect(Collectors.toList());
  }

  @Override
  public UserDto update(UserUpdateRequest request) {
    return UserFixture.userDto();
  }

  @Override
  public User create(SignupRequest request) {
    User user = User.builder()
            .lastName(request.getLastName())
            .firstName(request.getFirstName())
            .enabled(false)
            .birthday(request.getBirthday())
            .userRoles(UserFixture.userRole())
            .userRating(5D)
            .password(request.getPassword())
            .genDate(LocalDateTime.now())
            .build();
    return user;
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
