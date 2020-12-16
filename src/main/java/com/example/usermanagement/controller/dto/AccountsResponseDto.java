package com.example.usermanagement.controller.dto;

import com.example.usermanagement.domain.entity.Account;
import lombok.Getter;

import java.util.Date;

@Getter
public class AccountsResponseDto {
    private final long id;
    private final String accountId;
    private final String nickName;
    private final Date joinDate;
    private final Boolean isAdmin;

    public AccountsResponseDto(Account entity) {
        this.id = entity.getId();
        this.accountId = entity.getAccountId();
        this.nickName = entity.getNickName();
        this.joinDate = entity.getJoinDate();
        this.isAdmin = entity.getIsAdmin();
    }
}
