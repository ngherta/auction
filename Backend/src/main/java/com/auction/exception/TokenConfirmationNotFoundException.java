package com.auction.exception;

import javassist.NotFoundException;

public class TokenConfirmationNotFoundException extends RuntimeException {
  public TokenConfirmationNotFoundException(String message) {
    super(message);
  }
}
