package com.example.usermanagement.service;

import com.example.usermanagement.controller.dto.SignUpRequestDto;
import com.example.usermanagement.domain.entity.Account;
import com.example.usermanagement.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public void signUp(SignUpRequestDto dto) {
        Optional<Account> retrievalAccount = accountRepository.findByAccountId(dto.getAccountId());
        if (retrievalAccount.isPresent()) {
            throw new IllegalArgumentException("동일 아이디가 이미 존재합니다.");
        }
        Account account = new Account(dto.getAccountId(), dto.getPassword(), dto.getNickName(), false);
        accountRepository.save(account);
    }
}
