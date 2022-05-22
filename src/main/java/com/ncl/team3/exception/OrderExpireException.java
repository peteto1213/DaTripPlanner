package com.ncl.team3.exception;

public class OrderExpireException extends RuntimeException{
    public OrderExpireException(String message) {
        super(message);
    }
}
