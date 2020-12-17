package com.example.usermanagement.controller;

import com.example.usermanagement.controller.dto.ExceptionMessageDto;
import com.example.usermanagement.exception.AccountDuplicateException;
import com.example.usermanagement.exception.IdOrPasswordNotMatchException;
import com.example.usermanagement.exception.NoAccountException;
import com.example.usermanagement.exception.NoPermissionException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ExceptionController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessageDto> unknownError(final Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionMessageDto("internal server error"));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ExceptionMessageDto> illegalArgumentError(final IllegalArgumentException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest()
                .body(new ExceptionMessageDto(e.getMessage()));
    }

    @ExceptionHandler(AccountDuplicateException.class)
    public ResponseEntity<ExceptionMessageDto> duplicateAccount(final AccountDuplicateException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest()
                .body(new ExceptionMessageDto(e.getMessage()));
    }

    @ExceptionHandler(IdOrPasswordNotMatchException.class)
    public ResponseEntity<ExceptionMessageDto> idOrPasswordNotMatch(final IdOrPasswordNotMatchException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest()
                .body(new ExceptionMessageDto("Id또는 Password가 맞지 않습니다."));
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ExceptionMessageDto> invalidJwt(final JwtException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ExceptionMessageDto("유효하지 않은 Jwt 토큰 입니다."));
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<ExceptionMessageDto> expiredJwt(final ExpiredJwtException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ExceptionMessageDto("만료된 Jwt 토큰 입니다."));
    }

    @ExceptionHandler(NoPermissionException.class)
    public ResponseEntity<ExceptionMessageDto> haveNotPermission(final NoPermissionException e) {
        e.printStackTrace();
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(new ExceptionMessageDto(e.getMessage()));
    }

    @ExceptionHandler(NoAccountException.class)
    public ResponseEntity<ExceptionMessageDto> haveNotPermission(final NoAccountException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest()
                .body(new ExceptionMessageDto(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ExceptionMessageDto> haveNotPermission(final MethodArgumentTypeMismatchException e) {
        e.printStackTrace();
        return ResponseEntity.badRequest()
                .body(new ExceptionMessageDto("파라미터 타입을 잘못 입력했습니다. <" +
                        e.getValue() + "> -> <" +
                        e.getRequiredType() + "> 타입으로 입력해야합니다."));
    }
}
