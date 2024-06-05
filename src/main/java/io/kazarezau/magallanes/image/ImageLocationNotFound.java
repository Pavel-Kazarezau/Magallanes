package io.kazarezau.magallanes.image;

public class ImageLocationNotFound extends RuntimeException{
    public ImageLocationNotFound() {
    }

    public ImageLocationNotFound(final String message) {
        super(message);
    }

    public ImageLocationNotFound(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ImageLocationNotFound(final Throwable cause) {
        super(cause);
    }

    public ImageLocationNotFound(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
