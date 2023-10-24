package com.tabihoudai.tabihoudai_api.security.jwt.util;

import lombok.Data;

@Data
public class LoginInfoDto {
    private Long userIdx;
    private String email;
    private String name;
}
