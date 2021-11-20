package com.auction.service;

import com.auction.web.dto.UserDto;
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

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final ResetPasswordRepository resetPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    public void resetPasswordByEmail (String email) throws MessagingException, UnsupportedEncodingException {
        Optional<User> userOptional =  userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User with email" + email + " doesn't exist.");
        }

        User user = userOptional.get();

        ResetPasswordEntity resetPassword = new ResetPasswordEntity();

        String randomCode = RandomString.make(64);
        resetPassword.setCode(randomCode);
        resetPassword.setEnabled(false);
        resetPassword.setUser(user);
        resetPasswordRepository.save(resetPassword);

        sendVerificationEmail(user, resetPassword);
    }

    @Override
    public User changePasswordAfterReset(String email, String newPassword) {
        Optional<User> user =  userRepository.findByEmail(email);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User with email" + email + " doesn't exist.");
        }

        user.get().setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user.get());

        return user.get();
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
