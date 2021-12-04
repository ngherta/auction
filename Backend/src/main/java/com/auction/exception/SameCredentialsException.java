package com.auction.exception;

public class SameCredentialsException extends AuctionRuntimeException{
    public SameCredentialsException(String message) {
        super(message);
    }
}
