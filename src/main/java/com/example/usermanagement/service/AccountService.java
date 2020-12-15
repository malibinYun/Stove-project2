package com.example.usermanagement.service;

import com.example.usermanagement.controller.dto.SignUpRequestDto;
import com.example.usermanagement.domain.entity.Account;
import com.example.usermanagement.domain.repository.AccountRepository;
import com.example.usermanagement.exception.AccountDuplicateException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUp(SignUpRequestDto dto) {
        Optional<Account> retrievalAccount = accountRepository.findByAccountId(dto.getAccountId());
        if (retrievalAccount.isPresent()) {
            throw new AccountDuplicateException("동일 아이디가 이미 존재합니다.");
        }
        String encryptedPassword = bCryptPasswordEncoder.encode(dto.getPassword());
        Account account = new Account(dto.getAccountId(), encryptedPassword, dto.getNickName(), false);
        accountRepository.save(account);
    }
}
