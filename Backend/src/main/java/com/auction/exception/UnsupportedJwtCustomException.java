package com.auction.exception;

public class UnsupportedJwtCustomException extends RuntimeException{

  public UnsupportedJwtCustomException(String message) {
    super(message);
  }
}
