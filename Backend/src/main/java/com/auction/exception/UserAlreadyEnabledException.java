package com.auction.exception;

public class UserAlreadyEnabledException extends AuctionRuntimeException{
  public UserAlreadyEnabledException(String message) {
    super(message);
  }
}
