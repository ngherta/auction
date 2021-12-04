package com.auction.exception;

public class ExpiredJwtCustomException extends AuctionRuntimeException{

  public ExpiredJwtCustomException(String message) {
    super(message);
  }
}
