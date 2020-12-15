package com.example.usermanagement.exception;

public class IdOrPasswordNotMatchException extends IllegalArgumentException {
    public IdOrPasswordNotMatchException() {
        super();
    }

    public IdOrPasswordNotMatchException(String message) {
        super(message);
    }
}
