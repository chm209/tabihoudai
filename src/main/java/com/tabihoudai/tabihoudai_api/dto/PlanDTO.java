package com.tabihoudai.tabihoudai_api.dto;

import lombok.*;

import java.time.LocalDateTime;

public class PlanDTO {
    @AllArgsConstructor
    @Getter
    public static class planInfoResponse<T> {
        private T planList;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class mainPlanData {
        private long planIdx;
        private String planImgPath;
        private String nickName;
        private String title;
        private int likeCount;
        private String date;
    }
}