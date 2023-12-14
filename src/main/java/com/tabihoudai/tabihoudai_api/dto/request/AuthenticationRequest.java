package com.tabihoudai.tabihoudai_api.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthenticationRequest {

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String pw;
}
