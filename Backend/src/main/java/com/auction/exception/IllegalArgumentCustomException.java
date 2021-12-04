package com.auction.exception;

public class IllegalArgumentCustomException extends AuctionRuntimeException{

  public IllegalArgumentCustomException(String message) {
    super(message);
  }
}
