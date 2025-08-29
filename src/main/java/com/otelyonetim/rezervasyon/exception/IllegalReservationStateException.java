package com.otelyonetim.rezervasyon.exception;

public class IllegalReservationStateException extends RuntimeException {

    public IllegalReservationStateException(String message) {
        super(message);
    }

    public IllegalReservationStateException(String message, Throwable cause) {
        super(message, cause);
    }
}