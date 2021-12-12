package com.auction.service;

import com.auction.config.jwt.JwtUtils;
import com.auction.model.ResetPasswordEntity;
import com.auction.model.User;
import com.auction.repository.ResetPasswordRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.MailService;
import com.auction.service.interfaces.ResetPasswordService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
class ResetPasswordServiceImpl implements ResetPasswordService {

    private final UserRepository userRepository;
    private final ResetPasswordRepository resetPasswordRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    private final MailService mailService;

    @Transactional
    @Override
    public void resetPasswordByToken(String token) {
        String email = jwtUtils.getUserNameFromJwtToken(token);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email[" + email + "] doesn't exist!"));

        user.setEnabled(false);
        user = userRepository.save(user);

        ResetPasswordEntity resetPassword = ResetPasswordEntity.builder()
                .enabled(false)
                .code(RandomString.make(64))
                .user(user)
                .build();

        resetPasswordRepository.save(resetPassword);

        mailService.sendEmailForResetPassword(user, resetPassword);
    }

    @Override
    @Transactional
    public User changePasswordAfterReset(String email, String newPassword) {
        User user =  userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email" + email + " doesn't exist."));

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setEnabled(true);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean verify(String verificationCode) {
        ResetPasswordEntity resetPassword = resetPasswordRepository.findByCode(verificationCode);

        if (resetPassword == null || resetPassword.isEnabled()) {
            return false;
        } else {
            resetPassword.setCode(null);
            resetPassword.setEnabled(true);
            resetPasswordRepository.save(resetPassword);

            return true;
        }
    }
}
