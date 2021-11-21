package com.auction.exception;

public class UserAlreadyEnabledException extends RuntimeException{
  public UserAlreadyEnabledException(String message) {
    super(message);
  }
}
