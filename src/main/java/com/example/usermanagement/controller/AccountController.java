package com.example.usermanagement.controller;

import com.example.usermanagement.controller.dto.AccountsResponseDto;
import com.example.usermanagement.controller.resolver.annotation.AdminUser;
import com.example.usermanagement.domain.entity.Account;
import com.example.usermanagement.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/accounts")
    public ResponseEntity<List<AccountsResponseDto>> getAccountsByToken(@AdminUser Account adminUser) {
        List<AccountsResponseDto> accounts = accountService.getAllAccounts(adminUser);
        return ResponseEntity.ok(accounts);
    }

    @PatchMapping("/account/nickname/{id}")
    public ResponseEntity<Void> changeNickName(
            @AdminUser Account adminUser,
            @PathVariable long id,
            @RequestParam(value = "newNickName") String newNickName
    ) {
        accountService.changeNickName(id, newNickName);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/account/permission/{id}")
    public ResponseEntity<Void> changePermission(
            @AdminUser Account adminUser,
            @PathVariable long id,
            @RequestParam(value = "isAdmin") Boolean isAdmin
    ) {
        accountService.changePermission(id, isAdmin);
        return ResponseEntity.ok().build();
    }
}
