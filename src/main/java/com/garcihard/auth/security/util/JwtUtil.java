package com.garcihard.auth.security.util;

import com.garcihard.auth.security.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.security.PrivateKey;
import java.util.Date;

@Component
public class JwtUtil {

    @Value("${jwt.expiration}")
    private Long expiration;

    @Value("${jwt.issuer}")
    private String issuer;

    private final PrivateKey privateKey;

    public JwtUtil(@Value("${JWT_SECRET_PATH}")Resource privateKeyResource) throws Exception {
        this.privateKey = KeyReaderUtil.readPrivateKey(privateKeyResource.getInputStream());
    }

    public String generateToken(CustomUserDetails userDetails) {
        return Jwts.builder()
                .issuer(issuer)
                .subject(userDetails.getUserId().toString())
                .claim("username", userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }
}