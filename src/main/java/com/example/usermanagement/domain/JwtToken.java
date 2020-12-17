package com.example.usermanagement.domain;

import com.example.usermanagement.domain.entity.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.xml.bind.DatatypeConverter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class JwtToken {
    private static final String SECRET_KEY = "*malibin*";
    private static final long ACCESS_EXPIRE_TIME = TimeUnit.HOURS.toMillis(6);
    private static final long REFRESH_EXPIRE_TIME = TimeUnit.DAYS.toMillis(15);

    private static final String KEY_TYPE = "typ";
    private static final String KEY_ALGORITHM = "alg";
    private static final String KEY_ISSUER = "iss";
    private static final String KEY_ISSUED_AT = "iat";
    private static final String KEY_EXPIRED_TIME = "exp";
    private static final String KEY_USER_ID = "userId";

    private static final String TYPE_JWT = "JWT";
    private static final String HS256 = "HS256";
    private static final String ISSUER = "Account Auth Server";
    private static final String ACCESS = "access";
    private static final String REFRESH = "refresh";

    private final Claims payLoads;

    public JwtToken(String token) {
        this.payLoads = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token)
                .getBody();
    }

    public long getUserId() {
        return new Long((Integer) payLoads.get(KEY_USER_ID));
    }

    public Boolean isRefreshToken() {
        return payLoads.getId().equals(REFRESH);
    }

    public static void validate(String token) {
        Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET_KEY))
                .parseClaimsJws(token);
    }

    public static String generateAccessToken(Account account) {
        return generateTokenBuilder(account, ACCESS_EXPIRE_TIME)
                .setId(ACCESS)
                .compact();
    }

    public static String generateRefreshToken(Account account) {
        return generateTokenBuilder(account, REFRESH_EXPIRE_TIME)
                .setId(REFRESH)
                .compact();
    }

    private static JwtBuilder generateTokenBuilder(Account account, long expiredTime) {
        return Jwts.builder()
                .setHeader(createHeader())
                .setClaims(createClaims(account, expiredTime))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY);
    }

    private static Map<String, Object> createHeader() {
        Map<String, Object> map = new HashMap<>();
        map.put(KEY_TYPE, TYPE_JWT);
        map.put(KEY_ALGORITHM, HS256);
        return map;
    }

    private static Map<String, Object> createClaims(Account account, long expiredTime) {
        Map<String, Object> map = new HashMap<>();
        long now = System.currentTimeMillis();
        map.put(KEY_ISSUER, ISSUER);
        map.put(KEY_ISSUED_AT, now);
        map.put(KEY_EXPIRED_TIME, now + expiredTime);
        map.put(KEY_USER_ID, account.getId());
        return map;
    }
}
