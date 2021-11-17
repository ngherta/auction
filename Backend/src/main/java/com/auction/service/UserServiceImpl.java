package com.auction.service;

import com.auction.dto.UserDto;
import com.auction.dto.request.DeleteUserRequest;
import com.auction.dto.request.RegistrationRequest;
import com.auction.exception.SameCredentialsException;
import com.auction.exception.UserNotFoundException;
import com.auction.web.model.AuctionEvent;
import com.auction.web.model.User;
import com.auction.web.model.enums.UserRole;
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

    @Override
    public List<UserDto> getAll() {
        List<User> list = userRepository.findAll();
        List<UserDto> listDto = new ArrayList<>();
        for (User userEntity : list) {
            listDto.add(UserDto.from(userEntity));
        }
        return listDto;
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
