package com.tabihoudai.tabihoudai_api.controller.attraction;

import com.tabihoudai.tabihoudai_api.dto.attraction.AuthRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.AuthResponseDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.UserDto;
import com.tabihoudai.tabihoudai_api.service.attraction.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @RequestBody UserDto request
            ){
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthResponseDTO> register(
            @RequestBody AuthRequestDTO request
    ){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }

}
