package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.dto.request.AuthenticationRequest;
import com.tabihoudai.tabihoudai_api.dto.response.JsonWebTokenResponse;
import com.tabihoudai.tabihoudai_api.service.auth.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public JsonWebTokenResponse auth(@Validated @RequestBody AuthenticationRequest authRequest){
        return authenticationService.auth(authRequest);
    }
}
