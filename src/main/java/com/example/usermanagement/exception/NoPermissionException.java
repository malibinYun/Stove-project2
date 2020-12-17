package com.example.usermanagement.exception;

public class NoPermissionException extends IllegalArgumentException {
    public NoPermissionException() {
        super("권한이 없습니다.");
    }

    public NoPermissionException(String message) {
        super(message);
    }
}
