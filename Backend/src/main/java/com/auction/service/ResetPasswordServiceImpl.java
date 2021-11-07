package com.auction.service;

import com.auction.dto.UserDto;
import com.auction.exception.UserDoesntResetPassword;
import com.auction.web.model.ResetPasswordEntity;
import com.auction.web.model.User;
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

@Service
@RequiredArgsConstructor
public class ResetPasswordServiceImpl implements ResetPasswordService {

    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final ResetPasswordRepository resetPasswordRepository;
    private final PasswordEncoder passwordEncoder;

    public void resetPasswordByEmail (String email) throws MessagingException, UnsupportedEncodingException {
        User user;
        try {
            user = userRepository.findByEmail(email);
        }catch (UsernameNotFoundException e) {
            throw new UsernameNotFoundException("User with email" + email + " doesn't exist.");
        }

        ResetPasswordEntity resetPassword = new ResetPasswordEntity();

        String randomCode = RandomString.make(64);
        resetPassword.setCode(randomCode);
        resetPassword.setEnabled(false);
        resetPassword.setUser(user);
        resetPasswordRepository.save(resetPassword);

        sendVerificationEmail(user, resetPassword);
    }

    @Override
    public UserDto changePasswordAfterReset(String email, String newPassword) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("This user doesn't exist");
        }

        if (user.getPassword() != null) {
            throw new UserDoesntResetPassword("This user doesn't reset his password");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        UserDto userDto = UserDto.from(user);

        return userDto;
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
