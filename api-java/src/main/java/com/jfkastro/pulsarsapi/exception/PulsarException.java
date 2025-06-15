package com.jfkastro.pulsarsapi.exception;

public class PulsarException extends Exception {
    public PulsarException(String message) {
        super(message);
    }

    public PulsarException(String message, Throwable cause) {
        super(message, cause);
    }

    public PulsarException(Throwable cause) {
        super(cause);
    }
}
