package com.tabihoudai.tabihoudai_api.service.attraction;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;



@Service
public class JwtServiceImpl implements JwtService{

    private static final String SECRET_KEY = "25432A462D4A614E645267556A586E3272357538782F413F4428472B4B625065";
    private static final long EXPIRATION = 900000;
    private static final long REFRESHEXPIRATION = 86400000;


    public Claims extractAllClaims(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public Key getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }

}
