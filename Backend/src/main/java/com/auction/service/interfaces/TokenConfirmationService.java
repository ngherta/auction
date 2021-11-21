package com.auction.service.interfaces;

import com.auction.exception.TokenConfirmationNotFoundException;
import com.auction.exception.UserAlreadyEnabledException;
import com.auction.model.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface TokenConfirmationService {
  void confirm(String confirmation);

  void generate(User user) throws MessagingException, UnsupportedEncodingException;
}
