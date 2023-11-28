package com.tabihoudai.tabihoudai_api.dto.board;

import lombok.*;

import java.time.LocalDateTime;

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
}
