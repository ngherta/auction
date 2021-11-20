package com.auction.service;

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

    @Override
    public List<User> getAll() {
        List<User> list = userRepository.findAll();
        return list;
    }

    @Override
    public void deleteUserById(DeleteUserRequest request) throws UserNotFoundException {
        Optional<User> user = userRepository.findById(request.getUserId());

        if (!user.isPresent()) {
            throw new UserNotFoundException("User[" + request.getUserId() + "] doesn't exist.");
        }

        List<AuctionEvent> auctionEventList = auctionEventRepository.findByUser(user.get());
        for (AuctionEvent auctionEvent : auctionEventList) {
            auctionEventService.delete(auctionEvent);
        }

        auctionActionRepository.deleteAllByUser(user.get());

        //Chat

        log.info("Deleting user[" + request.getUserId() + "]");
        userRepository.delete(user.get());
    }
}
