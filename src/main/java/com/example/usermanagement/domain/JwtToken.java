package com.example.usermanagement.domain;

import com.example.usermanagement.domain.entity.Account;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JwtToken {
    private static final String SECRET_KEY = "*malibin*";
    private static final long EXPIRE_TIME = TimeUnit.DAYS.toMillis(1);

    public static String generate(Account account) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(account))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> map = new HashMap<>();
        map.put("typ", "JWT");
        map.put("alg", "HS256");
        return map;
    }

    private static Map<String, Object> createClaims(Account account) {
        Map<String, Object> map = new HashMap<>();
        long now = System.currentTimeMillis();
        map.put("iss", "Account Auth Server");
        map.put("iat", now);
        map.put("exp", now + EXPIRE_TIME);
        map.put("userId", account.getId());
        map.put("isAdmin", account.getIsAdmin());
        return map;
    }
}
