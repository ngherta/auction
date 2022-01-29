package com.auction.exception;

public class WrongPasswordException extends AuctionRuntimeException {

  public WrongPasswordException(String message) {
    super(message);
  }
}
