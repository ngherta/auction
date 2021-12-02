package com.auction.exception;

public class UserDoesntResetPasswordException extends RuntimeException{
    public UserDoesntResetPasswordException(String message) {
        super(message);
    }
}
