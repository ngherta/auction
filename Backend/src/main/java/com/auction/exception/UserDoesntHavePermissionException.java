package com.auction.exception;

public class UserDoesntHavePermissionException extends AuctionRuntimeException{
    public UserDoesntHavePermissionException(String message) {
        super(message);
    }
}
