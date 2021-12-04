package com.auction.exception;

public class UserNotFoundException extends AuctionRuntimeException {

  public UserNotFoundException(String message) {
    super(message);
  }
}
