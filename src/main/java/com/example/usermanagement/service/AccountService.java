package com.example.usermanagement.service;

import com.example.usermanagement.controller.dto.AccountsResponseDto;
import com.example.usermanagement.controller.dto.AuthenticationResponse;
import com.example.usermanagement.controller.dto.LoginRequestDto;
import com.example.usermanagement.controller.dto.SignUpRequestDto;
import com.example.usermanagement.domain.JwtToken;
import com.example.usermanagement.domain.entity.Account;
import com.example.usermanagement.domain.repository.AccountRepository;
import com.example.usermanagement.exception.AccountDuplicateException;
import com.example.usermanagement.exception.IdOrPasswordNotMatchException;
import com.example.usermanagement.exception.NoAccountException;
import com.example.usermanagement.exception.NoPermissionException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountService {

    private final AccountRepository accountRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUp(final SignUpRequestDto dto) {
        Optional<Account> retrievalAccount = accountRepository.findByAccountId(dto.getAccountId());
        if (retrievalAccount.isPresent()) {
            throw new AccountDuplicateException("동일 아이디가 이미 존재합니다.");
        }
        String encryptedPassword = bCryptPasswordEncoder.encode(dto.getPassword());
        Account account = new Account(dto.getAccountId(), encryptedPassword, dto.getNickName(), false);
        accountRepository.save(account);
    }

    public AuthenticationResponse login(final LoginRequestDto dto) {
        Account existAccount = accountRepository.findByAccountId(dto.getAccountId())
                .orElseThrow(() -> new IdOrPasswordNotMatchException(String.format("%s : id가 존재하지 않음", dto.getAccountId())));
        boolean isPasswordMatch = bCryptPasswordEncoder.matches(dto.getPassword(), existAccount.getPassword());
        if (!isPasswordMatch) {
            throw new IdOrPasswordNotMatchException("password가 일치하지 않음");
        }
        String accessToken = JwtToken.generate(existAccount);
        cacheToken(accessToken, existAccount);
        return new AuthenticationResponse(accessToken);
    }

    private void cacheToken(final String token, final Account account) {
        CacheManager cacheManager = CacheManager.create();
        Cache cache = cacheManager.getCache("tokenCache");
        cache.put(new Element(token, account));
    }

    public List<AccountsResponseDto> getAllAccounts(final Account adminUser) {
        if (adminUser.isNotAdmin()) {
            throw new NoPermissionException();
        }
        return accountRepository.findAll().stream()
                .map(AccountsResponseDto::new)
                .collect(Collectors.toList());
    }

    @Cacheable(key = "#token", value = "tokenCache")
    public Account getAccountByToken(final String token) {
        JwtToken jwtToken = new JwtToken(token);
        return accountRepository.findById(jwtToken.getUserId())
                .orElseThrow(() -> new NoAccountException("토큰에 해당하는 계정이 존재하지 않습니다."));
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
