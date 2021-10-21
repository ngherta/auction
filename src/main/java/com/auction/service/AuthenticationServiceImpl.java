package com.auction.service;

import com.auction.dto.UserDto;
import com.auction.dto.request.AuthRequest;
import com.auction.model.User;
import com.auction.repository.UserEntityRepository;
import com.auction.service.interfaces.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.AuthenticationFailedException;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final UserEntityRepository userEntityRepository;

    @Override
    public User authenticate(AuthRequest request)  {

        User user = userEntityRepository.findByLogin(request.getLogin()).get();
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
