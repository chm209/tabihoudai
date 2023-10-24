package com.tabihoudai.tabihoudai_api.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class MemberSignupResponseDto {
    private Long userIdx;
    private String email;
    private String nickname;
    private LocalDate birthday;
    private LocalDateTime regdate;
}
