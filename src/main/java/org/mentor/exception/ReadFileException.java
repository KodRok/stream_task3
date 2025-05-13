package org.mentor.exception;

public class ReadFileException extends RuntimeException {
    private final String message;

    public ReadFileException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

