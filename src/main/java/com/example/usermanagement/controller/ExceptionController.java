package com.example.usermanagement.controller;

import com.example.usermanagement.controller.dto.ExceptionMessageDto;
import com.example.usermanagement.exception.AccountDuplicateException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ExceptionMessageDto unknownError(Exception e) {
        e.printStackTrace();
        return new ExceptionMessageDto("internal server error");
    }

    @ExceptionHandler(AccountDuplicateException.class)
    public ExceptionMessageDto shortenUrlNotFound(AccountDuplicateException e) {
        e.printStackTrace();
        return new ExceptionMessageDto(e.getMessage());
    }
}
