package com.auction.service;

import com.auction.exception.TokenConfirmationNotFoundException;
import com.auction.model.ResetPasswordEntity;
import com.auction.model.User;
import com.auction.repository.ResetPasswordRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.MailService;
import com.auction.service.interfaces.ResetPasswordService;
import com.auction.service.interfaces.UserService;
import com.auction.web.dto.request.ChangePasswordRequest;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
class ResetPasswordServiceImpl implements ResetPasswordService {

    private final UserRepository userRepository;
    private final ResetPasswordRepository resetPasswordRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;
    private final UserService userService;

    @Transactional
    @Override
    public void resetPasswordByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User with email[" + email + "] doesn't exist!"));

        user.setEnabled(false);
        user = userRepository.save(user);

        Optional<ResetPasswordEntity> resetPasswordOpt = resetPasswordRepository.findByUser(user);

        ResetPasswordEntity resetPassword;
        if (resetPasswordOpt.isEmpty()) {
             resetPassword = ResetPasswordEntity.builder()
                    .enabled(false)
                    .code(RandomString.make(64))
                    .user(user)
                    .build();
            resetPasswordRepository.save(resetPassword);
        }
        else {
            resetPassword = resetPasswordOpt.get();
        }

        mailService.sendEmailForResetPassword(user, resetPassword);
    }

    @Override
    @Transactional
    public void changePasswordAfterReset(ChangePasswordRequest request,
                                         String code) {
        ResetPasswordEntity resetPassword = resetPasswordRepository.findByCode(code)
                .orElseThrow(() -> new TokenConfirmationNotFoundException("Token[" + code + "] doesn't exist!"));

        if (resetPassword.isEnabled()) {
            User user = resetPassword.getUser();

            user.setPassword(passwordEncoder.encode(request.getPassword()));
            user.setEnabled(true);
            userRepository.save(user);
            resetPasswordRepository.delete(resetPassword);
        }
    }

    @Override
    @Transactional
    public void verifyAndDisableUser(String code) {
        ResetPasswordEntity resetPassword = resetPasswordRepository.findByCode(code)
                .orElseThrow(() -> new TokenConfirmationNotFoundException("Token[" + code + "] doesn't exist!"));

        if (!resetPassword.isEnabled()) {
            userService.disable(resetPassword.getUser().getId());
            resetPassword.setEnabled(true);
            resetPasswordRepository.save(resetPassword);
        }
    }
}
