package io.kazarezau.magallanes.trip;

public class UnsupportedPointException extends RuntimeException{
    public UnsupportedPointException() {
    }

    public UnsupportedPointException(final String message) {
        super(message);
    }

    public UnsupportedPointException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UnsupportedPointException(final Throwable cause) {
        super(cause);
    }

    public UnsupportedPointException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
