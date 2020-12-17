package com.example.usermanagement.controller;

import com.example.usermanagement.controller.dto.AuthenticationResponse;
import com.example.usermanagement.controller.dto.LoginRequestDto;
import com.example.usermanagement.controller.dto.SignUpRequestDto;
import com.example.usermanagement.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signUp(@RequestBody SignUpRequestDto dto) {
        authService.signUp(dto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequestDto dto) {
        AuthenticationResponse response = authService.login(dto);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthenticationResponse> refreshToken(@RequestHeader("Authorization") String refreshToken) {
        AuthenticationResponse response = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(response);
    }
}
