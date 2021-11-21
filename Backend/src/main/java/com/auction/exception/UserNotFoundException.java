package com.auction.exception;

import javassist.NotFoundException;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(String message) {
    super(message);
  }
}
