package com.tabihoudai.tabihoudai_api.jwt.service;

import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.exception.NotFoundUserException;
import com.tabihoudai.tabihoudai_api.exception.TokenTypeException;
import com.tabihoudai.tabihoudai_api.jwt.config.JwtProperties;
import com.tabihoudai.tabihoudai_api.jwt.enums.JwtType;
import com.tabihoudai.tabihoudai_api.repository.users.UsersRepository;
import com.tabihoudai.tabihoudai_api.security.principal.UsersPrincipal;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private final JwtProperties jwtProperties;
    private final UsersRepository usersRepository;


    @Override
    public Jws<Claims> getClaims(final String token) {
        try {
            return Jwts.parser().setSigningKey(jwtProperties.getSecretKey()).parseClaimsJws(token);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("만료된 토큰");
        } catch (UnsupportedJwtException e) {
            throw new IllegalArgumentException("지원되지 않는 토큰");
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("잘못된 토큰");
        }
    }

    @Override
    public Authentication getAuthentication(final String token) {
        final Jws<Claims> claims = getClaims(token);
        if (isWrongType(claims, JwtType.ACCESS)) {
            throw TokenTypeException.EXCEPTION;
        }
        
        UsersEntity users = usersRepository.securityFindByEmail(claims.getBody().getSubject()).orElseThrow(() -> NotFoundUserException.EXCEPTION);
        UsersPrincipal usersPrincipal = new UsersPrincipal(users);

        return new UsernamePasswordAuthenticationToken(usersPrincipal, null, usersPrincipal.getAuthorities());
    }

    @Override
    public String generateAccessToken(final String email) {
        return Jwts.builder()
                .setHeaderParam(Header.JWT_TYPE, JwtType.ACCESS)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    @Override
    public String generateRefreshToken(final String email) {
        return Jwts.builder()
                .setHeaderParam(Header.JWT_TYPE, JwtType.REFRESH)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwtProperties.getRefreshExpiration()))
                .signWith(SignatureAlgorithm.HS256, jwtProperties.getSecretKey())
                .compact();
    }

    @Override
    public String extractTokenFromRequest(HttpServletRequest request) {
        return extractToken(request.getHeader(HttpHeaders.AUTHORIZATION));
    }

    @Override
    public String extractToken(final String token) {
        if (StringUtils.hasText(token)
                && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return token;
    }

    @Override
    public boolean isWrongType(
            final Jws<Claims> claims,
            final JwtType jwtType
    ) {
        return !(claims.getHeader().get(Header.JWT_TYPE).equals(jwtType.toString()));
    }
}
