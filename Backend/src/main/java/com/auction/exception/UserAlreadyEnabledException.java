package com.auction.exception;

public class UserAlreadyEnabledException extends Exception{
  public UserAlreadyEnabledException(String message) {
    super(message);
  }
}
