package ru.javawebinar.basejava.exception;

public class ResumeDeserializationException extends RuntimeException {
    public ResumeDeserializationException() {
    }

    public ResumeDeserializationException(String message) {
        super(message);
    }

    public ResumeDeserializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResumeDeserializationException(Throwable cause) {
        super(cause);
    }

    public ResumeDeserializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
