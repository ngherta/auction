package com.auction.exception;

import javassist.NotFoundException;

public class AuctionEventNotFoundException extends NotFoundException {

  public AuctionEventNotFoundException(String message) {
    super(message);
  }
}
