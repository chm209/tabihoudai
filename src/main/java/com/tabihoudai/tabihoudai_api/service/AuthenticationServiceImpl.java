package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.AuthDTO;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.jwt.service.JwtService;
import com.tabihoudai.tabihoudai_api.security.principal.UsersPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthDTO.JsonWebTokenResponse auth(@RequestBody AuthDTO.AuthenticationRequest authRequest) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPw()));
        UsersEntity user = ((UsersPrincipal) authenticate.getPrincipal()).getUsers();
        return AuthDTO.JsonWebTokenResponse.builder()
                .accessToken(jwtService.generateAccessToken(user.getEmail()))
                .refreshToken(jwtService.generateRefreshToken(user.getEmail()))
                .build();
    }
}
