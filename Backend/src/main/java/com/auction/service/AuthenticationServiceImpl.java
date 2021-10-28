package com.auction.service;

import com.auction.dto.request.AuthRequest;
import com.auction.model.User;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public User authenticate(AuthRequest request)  {

        User user = userRepository.findByLogin(request.getLogin()).get();
        if (user != null){
            if (passwordEncoder.matches(request.getPassword(), user.getPassword()))
                return user;
        }
        else {
//      throw new AuthenticationFailedException("Couldn't authenticate this user");
            return null;
        }
        return user;
    }
}
