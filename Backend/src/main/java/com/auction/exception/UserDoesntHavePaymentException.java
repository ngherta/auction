package com.auction.exception;

public class UserDoesntHavePaymentException extends RuntimeException{
    public UserDoesntHavePaymentException(String message) {
        super(message);
    }
}
