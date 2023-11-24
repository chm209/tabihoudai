package com.tabihoudai.tabihoudai_api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ExceptionDTO {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class ExceptionResponse {
        private int status;
        private String message;
    }
}
