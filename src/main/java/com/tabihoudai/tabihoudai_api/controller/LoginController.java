package com.tabihoudai.tabihoudai_api.controller;

import com.tabihoudai.tabihoudai_api.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("")
    public ResponseEntity<?> auth(@RequestParam String userIdx) {
        return ResponseEntity.ok(jwtUtils.createToken(userIdx));
    }
}
