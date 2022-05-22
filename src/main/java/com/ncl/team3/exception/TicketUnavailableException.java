package com.ncl.team3.exception;

public class TicketUnavailableException extends RuntimeException{
    public TicketUnavailableException(String message) {
        super(message);
    }
}
