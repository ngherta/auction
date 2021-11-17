package com.auction.service.interfaces;

import com.auction.exception.TokenConfirmationNotFound;
import com.auction.exception.UserAlreadyEnabledException;
import com.auction.web.model.User;

public interface TokenConfirmationService {
  void confirm(String confirmation) throws TokenConfirmationNotFound, UserAlreadyEnabledException;

  void generate(User user);
}
