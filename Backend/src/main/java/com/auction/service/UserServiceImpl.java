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

    @Transactional
    @Override
    public UserDto saveUser(RegistrationRequest request) throws SameCredentialsException {
        if (!this.validateUserCredentials(request)) {
            return null;
        }

        User user = new User();
        user.setLogin(request.getLogin());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setRole(UserRole.USER);
        user = userRepository.save(user);
        return UserDto.from(user);
    }

    public boolean validateUserCredentials(RegistrationRequest request) throws SameCredentialsException {
        if (userRepository.findByEmail(request.getEmail()) != null) {
            throw new SameCredentialsException("User with email[" + request.getEmail() + "] already exists");
        }
        if (userRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new SameCredentialsException("User with login[" + request.getLogin() + "] already exists");
        }
        return true;
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login).get();
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        Optional<User> userEntity;
        boolean isEmail = login.contains("@");
        if (isEmail){
            userEntity = Optional.ofNullable(userRepository.findByEmail(login));
        }
        else {
            userEntity = userRepository.findByLogin(login);
        }

        if (!userEntity.isPresent()) {
            if (passwordEncoder.matches(password, userEntity.get().getPassword())) {
                return userEntity.get();
            }
        }
        return null;
    }

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
