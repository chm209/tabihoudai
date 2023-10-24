package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.security.jwt.util.JwtTokenizer;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HelloApiController {
    private final JwtTokenizer jwtTokenizer;

    @GetMapping("/hello")
    public String hello(@RequestHeader("Authorization") String token) {
        Long userIdxFormToken = jwtTokenizer.getUserIdxFromToken(token);
        return "hello " + userIdxFormToken;
    }
}
