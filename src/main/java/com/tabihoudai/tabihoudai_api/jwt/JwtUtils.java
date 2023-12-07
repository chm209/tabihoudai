package com.tabihoudai.tabihoudai_api.jwt;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

@Component
public class JwtUtils {

    @Value("${application.security.secret}")
    private String secret;

    @Value("${application.security.accessExpTime}")
    private long accessExpTime;

    public String createToken(String userIdx){
        return Jwts.builder()
                .setSubject(userIdx)
                .addClaims(new HashMap<String, Object>() {{
                    put("roles", Arrays.asList("USER"));
                }})
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + accessExpTime))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secret)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
}
