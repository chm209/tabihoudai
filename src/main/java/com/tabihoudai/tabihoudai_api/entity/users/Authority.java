package com.tabihoudai.tabihoudai_api.entity.users;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Authority {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");
    private String key;
}