package com.auction.service.interfaces;

import com.auction.dto.UserDto;
import com.auction.web.model.ResetPasswordEntity;
import com.auction.web.model.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface ResetPasswordService {

    boolean verify(String verificationCode);

    void resetPasswordByEmail(String email) throws MessagingException, UnsupportedEncodingException;

    void sendVerificationEmail(User user, ResetPasswordEntity resetPassword)
            throws MessagingException, UnsupportedEncodingException;

    UserDto changePasswordAfterReset(String email, String newPassword);
}
