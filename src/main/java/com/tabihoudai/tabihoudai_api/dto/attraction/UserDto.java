package com.tabihoudai.tabihoudai_api.dto.attraction;

import com.tabihoudai.tabihoudai_api.entity.attraction.Role;
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
    private String block;
    private String profile;
    private String nickname;
    private Role role;

}
