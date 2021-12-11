package com.auction.service;

import com.auction.config.jwt.JwtUtils;
import com.auction.model.ResetPasswordEntity;
import com.auction.model.User;
import com.auction.repository.ResetPasswordRepository;
import com.auction.repository.UserRepository;
import com.auction.service.interfaces.ResetPasswordService;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.utility.RandomString;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
class ResetPasswordServiceImpl implements ResetPasswordService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final ResetPasswordRepository resetPasswordRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Transactional
    @Override
    public void resetPasswordByToken(String token) throws MessagingException, UnsupportedEncodingException {
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

        sendVerificationEmail(user, resetPassword);
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
    public void sendVerificationEmail(User user, ResetPasswordEntity resetPassword)
            throws MessagingException, UnsupportedEncodingException {
        String siteURL = "http://localhost:8080/";
        String toAddress = user.getEmail();
        String fromAddress = "gherta.nicolai@gmail.co";
        String senderName = "Film-manager";
        String subject = "Please reset your password";
        String content = "Dear [[name]],<br>"
                + "Please click the link below to reset your password:<br>"
                + "<h3><a href=\"[[URL]]\" target=\"_self\">VERIFY</a></h3>"
                + "Thank you,<br>"
                + "Your company name.";

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);

        content = content.replace("[[name]]", user.getFirstName() + " " + user.getLastName());
        String verifyURL = siteURL + "users/reset-password-check/"+ user.getId() +"?code=" + resetPassword.getCode();

        content = content.replace("[[URL]]", verifyURL);

        helper.setText(content, true);

        mailSender.send(message);
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
