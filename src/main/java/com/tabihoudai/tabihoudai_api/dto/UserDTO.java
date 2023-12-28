package com.tabihoudai.tabihoudai_api.dto;

import lombok.*;

import java.sql.Date;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
@ToString
public class UserDTO {
    private long user_idx;
    private String pw;
    private String email;
    private Date birthday;
    private byte block;
    private String profile;
    private String nickname;
}
