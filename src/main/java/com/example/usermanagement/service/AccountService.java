package com.example.usermanagement.service;

import com.example.usermanagement.controller.dto.AuthenticationResponse;
import com.example.usermanagement.controller.dto.LoginRequestDto;
import com.example.usermanagement.controller.dto.SignUpRequestDto;
import com.example.usermanagement.domain.JwtToken;
import com.example.usermanagement.domain.entity.Account;
import com.example.usermanagement.domain.repository.AccountRepository;
import com.example.usermanagement.exception.AccountDuplicateException;
import com.example.usermanagement.exception.IdOrPasswordNotMatchException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    synchronized public void signUp(SignUpRequestDto dto) {
        Optional<Account> retrievalAccount = accountRepository.findByAccountId(dto.getAccountId());
        if (retrievalAccount.isPresent()) {
            throw new AccountDuplicateException("동일 아이디가 이미 존재합니다.");
        }
        String encryptedPassword = bCryptPasswordEncoder.encode(dto.getPassword());
        Account account = new Account(dto.getAccountId(), encryptedPassword, dto.getNickName(), false);
        accountRepository.save(account);
    }

    synchronized public AuthenticationResponse login(LoginRequestDto dto) {
        Account existAccount = accountRepository.findByAccountId(dto.getAccountId())
                .orElseThrow(() -> new IdOrPasswordNotMatchException(String.format("%s : id가 존재하지 않음", dto.getAccountId())));
        boolean isPasswordMatch = bCryptPasswordEncoder.matches(dto.getPassword(), existAccount.getPassword());
        if (isPasswordMatch) {
            return new AuthenticationResponse(JwtToken.generate(existAccount));
        }
        throw new IdOrPasswordNotMatchException("password가 일치하지 않음");
    }
}
