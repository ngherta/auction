package com.auction.exception;

public class UserRoleNotFound extends RuntimeException {
  public UserRoleNotFound(String message) {
    super(message);
  }
}
