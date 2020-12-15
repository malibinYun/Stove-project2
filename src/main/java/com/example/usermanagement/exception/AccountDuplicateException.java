package com.example.usermanagement.exception;

public class AccountDuplicateException extends IllegalArgumentException {
    public AccountDuplicateException() {
        super();
    }

    public AccountDuplicateException(String message) {
        super(message);
    }
}
