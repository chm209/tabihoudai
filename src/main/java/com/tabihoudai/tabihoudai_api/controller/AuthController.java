package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.dto.AuthDTO;
import com.tabihoudai.tabihoudai_api.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthDTO.JsonWebTokenResponse auth(@Validated @RequestBody AuthDTO.AuthenticationRequest authRequset) {
        return authenticationService.auth(authRequset);
    }
}
