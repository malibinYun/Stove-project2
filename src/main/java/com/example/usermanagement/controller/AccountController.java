package com.example.usermanagement.controller;

import com.example.usermanagement.controller.dto.AuthenticationResponse;
import com.example.usermanagement.controller.dto.LoginRequestDto;
import com.example.usermanagement.controller.dto.SignUpRequestDto;
import com.example.usermanagement.domain.entity.Account;
import com.example.usermanagement.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequestDto dto) {
        accountService.signUp(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequestDto dto) {
        AuthenticationResponse response = accountService.login(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/accounts")
    public Account getAccountByToken(@RequestHeader("Authorization") String token) {
        return accountService.getAccountByToken(token);
    }
}
