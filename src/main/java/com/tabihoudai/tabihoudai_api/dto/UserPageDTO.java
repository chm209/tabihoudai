package com.tabihoudai.tabihoudai_api.dto;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserPageDTO {

    private java.sql.Date regDate;

    private String content;
}
