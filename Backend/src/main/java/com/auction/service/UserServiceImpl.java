package com.auction.service;

import com.auction.exception.UserNotFoundException;
import com.auction.exception.UserRoleNotFoundException;
import com.auction.exception.WrongPasswordException;
import com.auction.model.AuctionEvent;
import com.auction.model.Role;
import com.auction.model.User;
import com.auction.model.enums.UserRole;
import com.auction.model.mapper.Mapper;
import com.auction.repository.AuctionActionRepository;
import com.auction.repository.AuctionEventRepository;
import com.auction.repository.UserRepository;
import com.auction.repository.UserRoleRepository;
import com.auction.service.interfaces.AuctionEventService;
import com.auction.service.interfaces.ComplaintService;
import com.auction.service.interfaces.NotificationService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.UserDto;
import com.auction.web.dto.request.AddDefaultAddressRequest;
import com.auction.web.dto.request.SignupRequest;
import com.auction.web.dto.request.UpdatePasswordRequest;
import com.auction.web.dto.request.UserUpdateRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AuctionEventRepository auctionEventRepository;
    private final AuctionActionRepository auctionActionRepository;
    private final NotificationService notificationService;
    private AuctionEventService auctionEventService;
    private final Mapper<User, UserDto> userToDtoMapper;
    private final UserRoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private ComplaintService complaintService;

    //beans cycle
    @Autowired
    public void setAuctionEventService(@Lazy AuctionEventService auctionEventService) {
        this.auctionEventService = auctionEventService;
    }

    @Autowired
    public void setComplaintService(@Lazy ComplaintService complaintService) {
        this.complaintService = complaintService;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserDto> get(int page, int perPage) {
        Pageable pageable = PageRequest.of(page - 1, perPage);
        return userRepository.findAll(pageable).map(userToDtoMapper::map);
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {
        delete(findById(userId));
    }

    @Override
    @Transactional
    public void delete(User user) {
        List<AuctionEvent> auctionEventList = auctionEventRepository.findByUser(user);
        for (AuctionEvent auctionEvent : auctionEventList) {
            auctionEventService.delete(auctionEvent);
        }

        auctionActionRepository.deleteAllByUser(user);
        notificationService.deleteByUser(user);
        complaintService.deleteAllByUser(user);

        log.info("Deleting user[" + user.getId() + "]");
        userRepository.delete(user);
    }

    @Override
    @Transactional
    public UserDto update(UserUpdateRequest request) {
        User user = findById(request.getId());
        if (Boolean.TRUE.equals(request.getEnabled()) != user.isEnabled()) {
            user.setEnabled(request.getEnabled());
        }

        if (!request.getEmail().equals(user.getEmail())) {
            user.setEmail(request.getEmail());
        }

        if (!request.getFirstName().equals(user.getFirstName())) {
            user.setFirstName(request.getFirstName());
        }

        if (!request.getLastName().equals(user.getLastName())) {
            user.setLastName(request.getLastName());
        }

        if (!request.getBirthday().equals(user.getBirthday())) {
            user.setBirthday(request.getBirthday());
        }

        return userToDtoMapper.map(userRepository.save(user));
    }

    @Override
    @Transactional
    public User create(SignupRequest request) {
        User user = new User(request.getEmail(),
                             encoder.encode(request.getPassword()));

        Set<Role> roles = new HashSet<>(1);
        Role role = roleRepository.findByUserRole(UserRole.USER)
                .orElseThrow(() -> new UserRoleNotFoundException(UserRole.USER.name() + " doesn't exist."));
        roles.add(role);
        user.setUserRoles(roles);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setBirthday(request.getBirthday());
        user.setEnabled(true);
        user.setGenDate(LocalDateTime.now());
        user.setMoneyBalance(0D);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void updatePassword(UpdatePasswordRequest request) {
        User user = findById(request.getUserId());

        if (!encoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new WrongPasswordException("Wrong password!");
        }

        String newPasswordEncoded = encoder.encode(request.getNewPassword());
        if (user.getPassword().equals(newPasswordEncoded)) {
            throw new WrongPasswordException("Your old and new password are the same!");
        }

        user.setPassword(newPasswordEncoded);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void addDefaultAddress(AddDefaultAddressRequest request) {
        User user = findById(request.getUserId());
        user.setDefaultCountry(request.getCountry());
        user.setDefaultCity(request.getCity());
        user.setDefaultAddress(request.getAddress());
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

    @Transactional(readOnly = true)
    @Override
    public Optional<User> findMainAdmin() {
        return userRepository.findById(777L);
    }
}
