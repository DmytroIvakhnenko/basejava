package ru.javawebinar.basejava.exception;

public class ResumeSerializationException extends RuntimeException{
    public ResumeSerializationException() {
    }

    public ResumeSerializationException(String message) {
        super(message);
    }

    public ResumeSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResumeSerializationException(Throwable cause) {
        super(cause);
    }

    public ResumeSerializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
