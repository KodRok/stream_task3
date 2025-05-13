package org.mentor.exception;


public class OrderAdapterNotFoundException extends RuntimeException {
    private final String message;

    public OrderAdapterNotFoundException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}