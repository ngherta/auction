package com.auction.service.interfaces;

import com.auction.model.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface ResetPasswordService {

    boolean verify(String verificationCode);

    void resetPasswordByToken(String email) throws MessagingException, UnsupportedEncodingException;

    User changePasswordAfterReset(String email, String newPassword);
}
