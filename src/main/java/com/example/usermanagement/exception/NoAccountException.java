package com.example.usermanagement.exception;

public class NoAccountException extends IllegalArgumentException{
    public NoAccountException() {
        super("해당 계정이 존재하지 않습니다.");
    }

    public NoAccountException(String message) {
        super(message);
    }
}
