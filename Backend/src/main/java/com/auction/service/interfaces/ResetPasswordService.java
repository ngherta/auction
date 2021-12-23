package com.auction.service.interfaces;

import com.auction.web.dto.request.ChangePasswordRequest;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface ResetPasswordService {

    void verifyAndDisableUser(String verificationCode);

    void resetPasswordByEmail(String email) throws MessagingException, UnsupportedEncodingException;

    void changePasswordAfterReset(ChangePasswordRequest request,
                                  String code);
}
