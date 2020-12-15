package com.example.usermanagement.domain;

import com.example.usermanagement.domain.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JwtToken {
    private static final String SECRET_KEY = "*malibin*";
    private static final long EXPIRE_TIME = TimeUnit.DAYS.toMillis(1);

    private static final String KEY_TYPE = "typ";
    private static final String KEY_ALGORITHM = "alg";
    private static final String KEY_ISSUER = "iss";
    private static final String KEY_ISSUED_AT = "iat";
    private static final String KEY_EXPIRED_TIME = "exp";
    private static final String KEY_USER_ID = "userId";

    private static final String TYPE_JWT = "JWT";
    private static final String HS256 = "HS256";
    private static final String ISSUER = "Account Auth Server";

    private final Claims payLoads;

    public JwtToken(String token) {
        this.payLoads = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
    }

    public long getUserId() {
        return (long) payLoads.get(KEY_USER_ID);
    }

    public static String generate(Account account) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(account))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_TYPE, TYPE_JWT);
        map.put(KEY_ALGORITHM, HS256);
        return map;
    }

    private static Map<String, Object> createClaims(Account account) {
        Map<String, Object> map = new HashMap<>();
        long now = System.currentTimeMillis();
        map.put(KEY_ISSUER, ISSUER);
        map.put(KEY_ISSUED_AT, now);
        map.put(KEY_EXPIRED_TIME, now + EXPIRE_TIME);
        map.put(KEY_USER_ID, account.getId());
        return map;
    }
}
