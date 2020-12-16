package com.example.usermanagement.exception;

public class NoPermissionException extends IllegalArgumentException {
    public NoPermissionException() {
        super();
    }

    public NoPermissionException(String message) {
        super(message);
    }
}
