package com.tabihoudai.tabihoudai_api.jwt.service;

import com.tabihoudai.tabihoudai_api.jwt.enums.JwtType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;

public interface JwtService {
    Jws<Claims> getClaims(final String token);

    Authentication getAuthentication(final String token);

    String generateAccessToken(final String email);

    String generateRefreshToken(final String email);

    String extractTokenFromRequest(HttpServletRequest request);

    String extractToken(final String token);

    boolean isWrongType(final Jws<Claims> claims, final JwtType jwtType);
}
