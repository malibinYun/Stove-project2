package com.example.usermanagement.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@NoArgsConstructor
@Getter
@Entity
public class Account implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false, unique = true)
    private String accountId;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private Date joinDate;

    @Column(nullable = false)
    private Boolean isAdmin;

    public Account(String accountId, String password, String nickName, Boolean isAdmin) {
        this.accountId = accountId;
        this.password = password;
        this.nickName = nickName;
        this.joinDate = new Date();
        this.isAdmin = isAdmin;
    }
}
