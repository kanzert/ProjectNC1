package com.team.app.backend.exception;

public class QuizNotFoundException extends Exception {

    public QuizNotFoundException() {
    }

    public QuizNotFoundException(String message) {
        super(message);
    }

    public QuizNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public QuizNotFoundException(Throwable cause) {
        super(cause);
    }

    public QuizNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
