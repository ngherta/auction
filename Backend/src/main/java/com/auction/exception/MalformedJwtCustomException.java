package com.auction.exception;

public class MalformedJwtCustomException extends AuctionRuntimeException{

  public MalformedJwtCustomException(String message) {
    super(message);
  }
}
