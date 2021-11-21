package com.auction.exception;

public class MalformedJwtCustomException extends RuntimeException{

  public MalformedJwtCustomException(String message) {
    super(message);
  }
}
