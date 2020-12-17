package com.example.usermanagement.domain.repository;

import com.example.usermanagement.domain.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountId(String accountId);

    Optional<Account> findById(long id);

    Optional<Account> findByRefreshToken(String refreshToken);

    List<Account> findAll();
}
