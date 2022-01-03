package com.auction.exception;

public class UserDoesntResetPasswordException extends AuctionRuntimeException {
    public UserDoesntResetPasswordException(String message) {
        super(message);
    }
}
