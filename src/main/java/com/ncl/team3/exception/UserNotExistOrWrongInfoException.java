package com.ncl.team3.exception;

public class UserNotExistOrWrongInfoException extends RuntimeException{
    public UserNotExistOrWrongInfoException(String message) {
        super(message);
    }
}
