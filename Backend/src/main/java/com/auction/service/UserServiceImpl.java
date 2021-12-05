package com.auction.service;

import com.auction.exception.UserNotFoundException;
import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionActionRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.request.DeleteUserRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final AuctionEventRepository auctionEventRepository;
  private final AuctionActionRepository auctionActionRepository;
  private final Mapper<User, UserDto> userToDtoMapper;

  @Override
  @Transactional(readOnly = true)
  public Page<UserDto> get(int page, int perPage) {
    Pageable pageable = PageRequest.of(page - 1, perPage);

    return userRepository.findAll(pageable).map(userToDtoMapper::map);
  }

  @Override
  @Transactional
  public void deleteUserById(DeleteUserRequest request) {
    User user = findById(request.getUserId());

    List<AuctionEvent> auctionEventList = auctionEventRepository.findByUser(user);
    for (AuctionEvent auctionEvent : auctionEventList) {
//      auctionEventService.delete(auctionEvent);
    }

    auctionActionRepository.deleteAllByUser(user);

    //Chat

    log.info("Deleting user[" + request.getUserId() + "]");
    userRepository.delete(user);
  }

  @Override
  @Transactional
  public UserDto disable(Long userId) {
    User user = findById(userId);
    user.setEnabled(false);

    return userToDtoMapper.map(userRepository.save(user));
  }

  @Override
  @Transactional
  public UserDto enable(Long userId) {
    User user = findById(userId);
    user.setEnabled(true);

    return userToDtoMapper.map(userRepository.save(user));
  }

  @Override
  @Transactional(readOnly = true)
  public User findById(Long id) {
     return userRepository.findById(id)
            .orElseThrow(() -> new UserNotFoundException("User[" + id + "] doesn't exist"));
  }

  @Override
  @Transactional(readOnly = true)
  public UserDto getById(Long userId) {
    User user = findById(userId);
    return userToDtoMapper.map(user);
  }
}
