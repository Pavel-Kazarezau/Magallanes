package io.kazarezau.magallanes.email;

public class EmailException extends RuntimeException {

    public EmailException(final String message) {
        super(message);
    }

    public EmailException() {
    }

    public EmailException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
