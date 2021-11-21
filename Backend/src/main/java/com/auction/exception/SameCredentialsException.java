package com.auction.exception;

public class SameCredentialsException extends RuntimeException{
    public SameCredentialsException(String message) {
        super(message);
    }
}
