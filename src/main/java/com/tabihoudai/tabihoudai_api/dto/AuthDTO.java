package com.tabihoudai.tabihoudai_api.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

public class AuthDTO {

    @Getter
    @Setter
    public static class AuthenticationRequest {
        @Email
        @NotBlank
        private String email;
        private String pw;
    }

    @Setter
    @Getter
    public static class RefreshTokenRequest {
        @NotBlank
        private String refreshToken;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class ExceptionResponse {
        private int status;
        private String message;
    }

    @Getter
    @Builder
    public static class JsonWebTokenResponse {
        private String accessToken;
        private String refreshToken;
    }
}
