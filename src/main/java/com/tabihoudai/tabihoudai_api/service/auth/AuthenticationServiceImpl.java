package com.tabihoudai.tabihoudai_api.service.auth;

import com.tabihoudai.tabihoudai_api.dto.request.AuthenticationRequest;
import com.tabihoudai.tabihoudai_api.dto.response.JsonWebTokenResponse;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.jwt.service.JwtService;
import com.tabihoudai.tabihoudai_api.security.principal.UsersPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService{

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JsonWebTokenResponse auth(AuthenticationRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPw()));
        UsersEntity users = ((UsersPrincipal) authenticate.getPrincipal()).getUsers();

        return JsonWebTokenResponse.builder()
                .accessToken(jwtService.generateAccessToken(users.getEmail()))
                .refreshToken(jwtService.generateRefreshToken(users.getEmail()))
                .build();
    }
}
