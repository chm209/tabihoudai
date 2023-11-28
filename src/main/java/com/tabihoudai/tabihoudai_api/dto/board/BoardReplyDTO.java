package com.tabihoudai.tabihoudai_api.dto.board;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class BoardReplyDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class getReplyDTO {
        private String nickname;
        private String content;
        private String profile;
        private LocalDateTime regDate;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class reportReplyDTO{
        private long replyIdx;
        private String content;
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class replyRegisterDTO {
        private long boardIdx;
        private UUID usersIdx;
        private String content;
    }
}
