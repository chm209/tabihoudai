package com.tabihoudai.tabihoudai_api.dto.board;

import lombok.*;

import java.util.UUID;

public class BoardLikeDTO {

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class LikeInsertDTO{
        private byte Flag;
        private Long boardIdx;
        private UUID userIdx;
    }
}
