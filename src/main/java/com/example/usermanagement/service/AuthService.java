package com.example.usermanagement.service;

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
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

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
        validatePassword(dto, existAccount);

        String accessToken = JwtToken.generateAccessToken(existAccount);
        String refreshToken = JwtToken.generateRefreshToken(existAccount);
        cacheToken(accessToken, existAccount);
        saveRefreshToken(existAccount, refreshToken);
        return new AuthenticationResponse(accessToken, refreshToken);
    }

    private void validatePassword(final LoginRequestDto dto, final Account account) {
        boolean isPasswordMatch = bCryptPasswordEncoder.matches(dto.getPassword(), account.getPassword());
        if (!isPasswordMatch) {
            throw new IdOrPasswordNotMatchException("password가 일치하지 않음");
        }
    }

    private void saveRefreshToken(final Account account, String refreshToken) {
        account.changeRefreshToken(refreshToken);
        accountRepository.save(account);
    }

    private void cacheToken(final String token, final Account account) {
        CacheManager cacheManager = CacheManager.create();
        Cache cache = cacheManager.getCache("tokenCache");
        cache.put(new Element(token, account));
    }

    @Cacheable(key = "#token", value = "tokenCache")
    public Account getAccountByAccessToken(final String token) {
        JwtToken jwtToken = new JwtToken(token);
        if (jwtToken.isRefreshToken()) {
            throw new NoPermissionException("유효한 토큰이 아닙니다.");
        }
        return accountRepository.findById(jwtToken.getUserId())
                .orElseThrow(() -> new NoAccountException("토큰에 해당하는 계정이 존재하지 않습니다."));
    }

    public AuthenticationResponse refreshToken(final String receiveToken) {
        JwtToken.validate(receiveToken);
        Account account = accountRepository.findByRefreshToken(receiveToken)
                .orElseThrow(() -> new IllegalArgumentException("유효한 토큰이 아닙니다."));
        String accessToken = JwtToken.generateAccessToken(account);
        String refreshToken = JwtToken.generateRefreshToken(account);
        cacheToken(accessToken, account);
        saveRefreshToken(account, refreshToken);
        return new AuthenticationResponse(accessToken, refreshToken);
    }
}
