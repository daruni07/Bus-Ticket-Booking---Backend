package com.springboot.busticketbooking.exception;

public class InvalidCallException extends RuntimeException {
    public InvalidCallException(String message) {
        super(message);
    }
}
