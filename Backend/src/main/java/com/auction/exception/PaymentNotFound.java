package com.auction.exception;

public class PaymentNotFound extends AuctionRuntimeException{
  public PaymentNotFound(String message) {
    super(message);
  }
}
