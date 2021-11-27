package com.auction.exception;

public class UserDoesntHavePermissionException extends RuntimeException{
    public UserDoesntHavePermissionException(String message) {
        super(message);
    }
}
