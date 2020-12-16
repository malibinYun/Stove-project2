package com.example.usermanagement.controller;

import com.example.usermanagement.controller.dto.ExceptionMessageDto;
import com.example.usermanagement.exception.AccountDuplicateException;
import com.example.usermanagement.exception.IdOrPasswordNotMatchException;
import com.example.usermanagement.exception.NoPermissionException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessageDto> unknownError(Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionMessageDto("internal server error"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionMessageDto> illegalArgumentError(IllegalArgumentException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest()
                .body(new ExceptionMessageDto(e.getMessage()));
    }

    @ExceptionHandler(AccountDuplicateException.class)
    public ResponseEntity<ExceptionMessageDto> duplicateAccount(AccountDuplicateException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest()
                .body(new ExceptionMessageDto(e.getMessage()));
    }

    @ExceptionHandler(IdOrPasswordNotMatchException.class)
    public ResponseEntity<ExceptionMessageDto> idOrPasswordNotMatch(IdOrPasswordNotMatchException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest()
                .body(new ExceptionMessageDto("Id또는 Password가 맞지 않습니다."));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionMessageDto> invalidJwt(JwtException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ExceptionMessageDto("유효하지 않은 Jwt 토큰 입니다."));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionMessageDto> expiredJwt(ExpiredJwtException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ExceptionMessageDto("만료된 Jwt 토큰 입니다."));
    }

    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<ExceptionMessageDto> haveNotPermission(NoPermissionException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ExceptionMessageDto("권한이 없습니다."));
    }
}
