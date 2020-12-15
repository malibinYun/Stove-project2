package com.example.usermanagement.exception;

public class AccountDuplicateException extends IllegalArgumentException {
    public AccountDuplicateException() {
        super("동일 아이디가 이미 존재합니다.");
    }

    public AccountDuplicateException(String message) {
        super(message);
    }
}
