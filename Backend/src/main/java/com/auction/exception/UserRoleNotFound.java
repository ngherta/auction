package com.auction.exception;

import javassist.NotFoundException;

public class UserRoleNotFound extends NotFoundException {
  public UserRoleNotFound(String message) {
    super(message);
  }
}
