package com.tabihoudai.tabihoudai_api.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

public class BoardDTO {
    @AllArgsConstructor
    @Getter
    public static class boardInfoResponse<T> {
        private T boardList;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class mainBoardData {
        private long boardIdx;
        private String title;
        private LocalDateTime date;
        private String nickname;
    }
}
