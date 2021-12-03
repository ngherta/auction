package com.auction.exception;

import javassist.NotFoundException;

public class TokenConfirmationNotFoundException extends AuctionRuntimeException {
  public TokenConfirmationNotFoundException(String message) {
    super(message);
  }
}
