package com.tabihoudai.tabihoudai_api.dto.board;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BoardListDTO {
    private long boardIdx;
    private String title;
    private LocalDateTime regDate;
    private int visitCount;
    private String nickname;
}
