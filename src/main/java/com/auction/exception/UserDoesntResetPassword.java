package com.auction.exception;

public class UserDoesntResetPassword extends RuntimeException{
    public UserDoesntResetPassword(String message) {
        super(message);
    }
}
