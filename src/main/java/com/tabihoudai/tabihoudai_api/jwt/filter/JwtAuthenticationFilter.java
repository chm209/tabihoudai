package com.tabihoudai.tabihoudai_api.jwt.filter;

import com.tabihoudai.tabihoudai_api.jwt.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, ExpiredJwtException {
        String token = jwtService.extractTokenFromRequest(request);
        if (token != null){//토큰이 유효한지 검사함
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(jwtService.getAuthentication(token));
        }
        filterChain.doFilter(request, response);
    }
}
