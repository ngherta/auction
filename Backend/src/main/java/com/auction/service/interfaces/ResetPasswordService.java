package com.auction.service.interfaces;

import com.auction.web.dto.UserDto;
import com.auction.model.ResetPasswordEntity;
import com.auction.model.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface ResetPasswordService {

    boolean verify(String verificationCode);

    void resetPasswordByEmail(String email) throws MessagingException, UnsupportedEncodingException;

    void sendVerificationEmail(User user, ResetPasswordEntity resetPassword)
            throws MessagingException, UnsupportedEncodingException;

    User changePasswordAfterReset(String email, String newPassword);
}
