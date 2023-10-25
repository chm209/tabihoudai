package com.tabihoudai.tabihoudai_api.dto.board;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BoardDTO {
    private long boardIdx;
    private int category;
    private String title;
    private String content;
    private LocalDateTime regDate;
    private int visitCount;
    private String nickname;
    private int replyCount;
    private int likeCount;
}
