package com.auction.exception;

public class CategoryAlreadyExistException extends AuctionRuntimeException{
  public CategoryAlreadyExistException(String message) {
    super(message);
  }
}
