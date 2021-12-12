package com.auction.exception;

public class EmailAlreadyExistException extends AuctionRuntimeException{
    public EmailAlreadyExistException(String message) {
        super(message);
    }
}
