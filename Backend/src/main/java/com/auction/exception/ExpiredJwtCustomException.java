package com.auction.exception;

public class ExpiredJwtCustomException extends RuntimeException{

  public ExpiredJwtCustomException(String message) {
    super(message);
  }
}
