package com.tabihoudai.tabihoudai_api.service.attraction;

import io.jsonwebtoken.Claims;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.Map;

public interface JwtService {

    Claims extractAllClaims(String token);

    Key getSignInKey();
    String extractUsername(String token);
    String generateToken(Map<String, Object> extraClaims,
                         UserDetails userDetails);
    String generateToken(UserDetails userDetails);
    boolean isTokenValid(String token,UserDetails userDetails);
}
