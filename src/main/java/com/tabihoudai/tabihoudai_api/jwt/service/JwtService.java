package com.tabihoudai.tabihoudai_api.jwt.service;

import com.tabihoudai.tabihoudai_api.jwt.enums.JwtType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;


public interface JwtService {
    public String generateAccessToken(final String email);

    public String generateRefreshToken(final String email);

    public Jws<Claims> getClaims(final String token);

    String extractTokenFromRequest(HttpServletRequest request);

    String extractToken(final String token);

    boolean isWrongType(
            final Jws<Claims> claims,
            final JwtType jwtType
    );

    Authentication getAuthentication(final String token);
}
