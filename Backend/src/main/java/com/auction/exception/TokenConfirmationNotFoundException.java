package com.auction.exception;

import javassist.NotFoundException;

public class TokenConfirmationNotFoundException extends NotFoundException {
  public TokenConfirmationNotFoundException(String message) {
    super(message);
  }
}
