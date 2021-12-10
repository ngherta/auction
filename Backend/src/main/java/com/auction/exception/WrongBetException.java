package com.auction.exception;

public class WrongBetException extends AuctionRuntimeException{
  public WrongBetException(String message) {
    super(message);
  }
}
