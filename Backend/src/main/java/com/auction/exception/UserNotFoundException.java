package com.auction.exception;

import javassist.NotFoundException;

public class UserNotFoundException extends NotFoundException {

  public UserNotFoundException(String message) {
    super(message);
  }
}
