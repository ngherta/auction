package com.auction.exception;

public class UserDoesntHavePaymentException extends AuctionRuntimeException{
    public UserDoesntHavePaymentException(String message) {
        super(message);
    }
}
