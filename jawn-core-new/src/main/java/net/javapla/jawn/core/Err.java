package net.javapla.jawn.core;

import javax.annotation.Nullable;

@SuppressWarnings("serial")
public class Err extends RuntimeException {
    
    public static class BadMediaType extends Err {
        public BadMediaType(final String message) {
            super(Status.BAD_REQUEST, message);
        }
    }

    public static class Missing extends Err {
        public Missing(final String message) {
            super(Status.BAD_REQUEST, message);
        }
    }
    
    public static class ParsableError extends Err {
        public ParsableError(final Throwable err) {
            super(Status.UNPROCESSABLE_ENTITY, err);
        }
    }
    
    private final int statusCode;
    
    public Err(final Status status, final String message, final Throwable cause) {
        super(message(status, message), cause);
        this.statusCode = status.value();
    }
    
    public Err(final int status, final String message, final Throwable cause) {
        super(message("", status, message), cause);
        this.statusCode = status;
    }
    
    public Err(final Status status, final String message) {
        super(message(status, message));
        this.statusCode = status.value();
    }
    
    public Err(final int status, final String message) {
        this(Status.valueOf(status), message);
    }
    
    public Err(final Status status, final Throwable cause) {
        super(message(status, null), cause);
        this.statusCode = status.value();
    }
    
    public Err(final int status, final Throwable cause) {
        this(Status.valueOf(status), cause);
    }
    
    public Err(final Status status) {
        super(message(status, null));
        this.statusCode = status.value();
    }

    public Err(final int status) {
        this(Status.valueOf(status));
    }
    
    public int statusCode() {
        return statusCode;
    }
    
    /**
     * Build an error message using the HTTP status.
     *
     * @param status The HTTP Status.
     * @param tail A message to append.
     * @return An error message.
     */
    private static String message(final Status status, @Nullable final String tail) {
        return message(status.reason(), status.value(), tail);
    }

    /**
     * Build an error message using the HTTP status.
     *
     * @param reason Reason.
     * @param status The Status.
     * @param tail A message to append.
     * @return An error message.
     */
    private static String message(final String reason, final int status, @Nullable  final String tail) {
        return reason + "(" + status + ")" + (tail == null ? "" : ": " + tail);
    }
}
