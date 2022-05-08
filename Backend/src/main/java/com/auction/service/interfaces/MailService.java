package com.auction.service.interfaces;

import com.auction.model.ResetPasswordEntity;
import com.auction.model.TokenConfirmation;
import com.auction.model.User;

public interface MailService {

  void sendEmailConfirmation(TokenConfirmation tokenConfirmation);

  void sendEmailForResetPassword(User user, ResetPasswordEntity resetPassword);
}
