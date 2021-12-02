package com.auction.service;

import com.auction.exception.UserNotFoundException;
import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.model.mapper.UserToDtoMapper;
import com.auction.repository.AuctionActionRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.request.DeleteUserRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final AuctionEventService auctionEventService;
  private final AuctionEventRepository auctionEventRepository;
  private final AuctionActionRepository auctionActionRepository;
  private final UserToDtoMapper userToDtoMapper;

  @Override
  @Transactional(readOnly = true)
  public List<UserDto> getAll() {
    List<User> list = userRepository.findAll();
    return userToDtoMapper.mapList(list);
  }

  @Override
  @Transactional
  public void deleteUserById(DeleteUserRequest request) {
    User user = findById(request.getUserId());

    List<AuctionEvent> auctionEventList = auctionEventRepository.findByUser(user);
    for (AuctionEvent auctionEvent : auctionEventList) {
      auctionEventService.delete(auctionEvent);
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
}
