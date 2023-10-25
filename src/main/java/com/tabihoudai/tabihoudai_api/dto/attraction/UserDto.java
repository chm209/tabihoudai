package com.tabihoudai.tabihoudai_api.dto.attraction;

import lombok.*;

import java.time.LocalDate;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserDto {

    private byte[] userIdx;
    private String pw;
    private String email;
    private LocalDate birthday;
    private String block;
    private String profile;
    private String nickname;

}
