package com.example.usermanagement.controller.dto;

import lombok.Getter;

@Getter
public class ExceptionMessageDto {
    private final String message;

    public ExceptionMessageDto(String message) {
        this.message = message;
    }
}
