package com.auction.service;

import com.auction.dto.UserDto;
import com.auction.dto.request.RegistrationRequest;
import com.auction.exception.SameCredentialsException;
import com.auction.model.User;
import com.auction.model.enums.UserRole;
import com.auction.repository.UserEntityRepository;
import com.auction.service.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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
        user = userEntityRepository.save(user);
        return UserDto.from(user);
    }

    public boolean validateUserCredentials(RegistrationRequest request) throws SameCredentialsException {
        if (userEntityRepository.findByEmail(request.getEmail()) != null) {
            throw new SameCredentialsException("User with email[" + request.getEmail() + "] already exists");
        }
        if (userEntityRepository.findByLogin(request.getLogin()).isPresent()) {
            throw new SameCredentialsException("User with login[" + request.getLogin() + "] already exists");
        }
        return true;
    }

    @Override
    public User findByLogin(String login) {
        return userEntityRepository.findByLogin(login).get();
    }

    @Override
    public User findByLoginAndPassword(String login, String password) {
        User userEntity = findByLogin(login);
        if (userEntity != null) {
            if (passwordEncoder.matches(password, userEntity.getPassword())) {
                return userEntity;
            }
        }
        return null;
    }

    @Override
    public List<UserDto> getAll() {
        List<User> list = userEntityRepository.findAll();
        List<UserDto> listDto = new ArrayList<>();
        for (User userEntity : list) {
            listDto.add(UserDto.from(userEntity));
        }
        return listDto;
    }
}
