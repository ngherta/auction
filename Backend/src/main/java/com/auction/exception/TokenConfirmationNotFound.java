package com.auction.exception;

import javassist.NotFoundException;

public class TokenConfirmationNotFound extends NotFoundException {
  public TokenConfirmationNotFound(String message) {
    super(message);
  }
}
