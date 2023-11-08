package com.tabihoudai.tabihoudai_api.dto.board;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class BoardDTO {
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class BoardRegisterDTO {
        private long boardIdx;
        private UUID usersIdx;
        private int category;
        private String title;
        private String content;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class BoardViewDTO {
        private long boardIdx;
        private int category;
        private String title;
        private String content;
        private LocalDateTime regDate;
        private int visitCount;
        private String nickname;
        private int replyCount;
        private int likeCount;
        private int unLikeCount;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class BoardListDTO {
        private long boardIdx;
        private String title;
        private LocalDateTime regDate;
        private int visitCount;
        private String nickname;
    }

}
