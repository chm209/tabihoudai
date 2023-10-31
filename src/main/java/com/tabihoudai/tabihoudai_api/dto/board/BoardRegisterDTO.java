package com.tabihoudai.tabihoudai_api.dto.board;

import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BoardRegisterDTO {
    private long boardIdx;
    private UUID usersIdx;
    private int category;
    private String title;
    private String content;
}
