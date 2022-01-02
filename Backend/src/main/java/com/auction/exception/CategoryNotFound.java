package com.auction.exception;

public class CategoryNotFound extends AuctionRuntimeException{
  public CategoryNotFound(String message) {
    super(message);
  }
}
