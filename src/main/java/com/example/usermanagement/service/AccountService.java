package com.example.usermanagement.service;

import com.example.usermanagement.controller.dto.AccountResponseDto;
import com.example.usermanagement.domain.entity.Account;
import com.example.usermanagement.domain.repository.AccountRepository;
import com.example.usermanagement.exception.NoAccountException;
import com.example.usermanagement.exception.NoPermissionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;

    public List<AccountResponseDto> getAllAccounts(final Account adminUser) {
        if (adminUser.isNotAdmin()) {
            throw new NoPermissionException();
        }
        return accountRepository.findAll().stream()
                .map(AccountResponseDto::new)
                .collect(Collectors.toList());
    }

    public void changeNickName(final long id, final String newNickName) {
        Account account = accountRepository.findById(id)
                .orElseThrow(NoAccountException::new);
        account.changeNickname(newNickName);
        accountRepository.save(account);
    }

    public void changePermission(final long id, final Boolean isAdmin) {
        Account account = accountRepository.findById(id)
                .orElseThrow(NoAccountException::new);
        account.changePermission(isAdmin);
        accountRepository.save(account);
    }
}
