package com.auction.exception;

import javassist.NotFoundException;

public class AuctionEventNotFoundException extends RuntimeException {

  public AuctionEventNotFoundException(String message) {
    super(message);
  }
}
