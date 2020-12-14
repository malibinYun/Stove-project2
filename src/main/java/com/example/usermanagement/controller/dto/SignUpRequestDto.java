package com.example.usermanagement.controller.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class SignUpRequestDto {
    private String accountId;
    private String password;
    private String nickName;
}
