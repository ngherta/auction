package com.auction.exception;

public class IllegalArgumentCustomException extends RuntimeException{

  public IllegalArgumentCustomException(String message) {
    super(message);
  }
}
