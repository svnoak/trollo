package com.todo.exception;

/**
 * Exception thrown when a request is malformed.
 */
public class BadRequestException extends RuntimeException {

    /**
     * Constructor.
     * @param message The message.
     */
    public BadRequestException(String message) {
        super(message);
    }
}
