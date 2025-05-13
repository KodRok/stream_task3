package org.mentor.exception;

public class WriteFileException extends RuntimeException {
    private final String message;

    public WriteFileException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
