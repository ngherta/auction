package com.auction.service;

import com.auction.config.jwt.JwtUtils;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Autowired
  private UserRepository userRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Autowired
  private AuctionEventService auctionEventService;
  @Autowired
  private AuctionEventRepository auctionEventRepository;
  @Autowired
  private AuctionActionRepository auctionActionRepository;
  @Autowired
  private JwtUtils jwtUtils;

  @Override
  public List<User> getAll() {
    List<User> list = userRepository.findAll();
    return list;
  }

  @Override
  public void deleteUserById(DeleteUserRequest request) throws UserNotFoundException {

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
  public User disable(Long userId) throws UserNotFoundException {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User[" + userId + "] doesn't exist!"));
    user.setEnabled(false);

    return userRepository.save(user);
  }

  @Override
  @Transactional
  public User enable(Long userId) throws UserNotFoundException {
    User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User[" + userId + "] doesn't exist!"));
    user.setEnabled(true);

    return userRepository.save(user);
  }
}
