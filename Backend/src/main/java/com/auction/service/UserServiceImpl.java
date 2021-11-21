package com.auction.service;

import com.auction.config.jwt.JwtUtils;
import com.auction.model.mapper.UserToDtoMapper;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.request.DeleteUserRequest;
import com.auction.exception.UserNotFoundException;
import com.auction.model.AuctionEvent;
import com.auction.model.User;
import com.auction.repository.AuctionActionRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
  public List<UserDto> getAll() {
    List<User> list = userRepository.findAll();
    return userToDtoMapper.mapList(list);
  }

  @Override
  public void deleteUserById(DeleteUserRequest request) {

    User user = userRepository.findById(request.getUserId())
            .orElseThrow(() -> new UserNotFoundException("User[" + request.getUserId() + "] doesn't exist!"));

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
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User[" + userId + "] doesn't exist!"));
    user.setEnabled(false);

    return userToDtoMapper.map(userRepository.save(user));
  }

  @Override
  @Transactional
  public UserDto enable(Long userId) {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User[" + userId + "] doesn't exist!"));
    user.setEnabled(true);

    return userToDtoMapper.map(userRepository.save(user));
  }
}
