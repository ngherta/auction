package com.auction.exception;

public class UnsupportedJwtCustomException extends AuctionRuntimeException{

  public UnsupportedJwtCustomException(String message) {
    super(message);
  }
}
