package com.example.usermanagement.controller;

import com.example.usermanagement.controller.dto.ExceptionMessageDto;
import com.example.usermanagement.exception.AccountDuplicateException;
import com.example.usermanagement.exception.IdOrPasswordNotMatchException;
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
    public ExceptionMessageDto duplicateAccount(AccountDuplicateException e) {
        e.printStackTrace();
        return new ExceptionMessageDto(e.getMessage());
    }

    @ExceptionHandler(IdOrPasswordNotMatchException.class)
    public ExceptionMessageDto idOrPasswordNotMatch(IdOrPasswordNotMatchException e){
        e.printStackTrace();
        return new ExceptionMessageDto("Id또는 Password가 맞지 않습니다.");
    }
}
