package com.auction.exception;

public class SameCredentialsException extends Exception{
    public SameCredentialsException(String message) {
        super(message);
    }
}
