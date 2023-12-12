package com.tabihoudai.tabihoudai_api.service.attraction;

import io.jsonwebtoken.Claims;

import java.security.Key;

public interface JwtService {

    Claims extractAllClaims(String token);

    Key getSignInKey();
}
