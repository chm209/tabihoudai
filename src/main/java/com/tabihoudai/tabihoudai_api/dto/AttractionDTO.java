package com.tabihoudai.tabihoudai_api.dto;

import lombok.*;

public class AttractionDTO {
    @AllArgsConstructor
    @Getter
    public static class attractionInfoResponse<T> {
        private T areaAttractionList;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class mainAttractionData {
        private long attrIdx;
        private String path;
        private String name;
        private String address;
        private String tag;
    }

    @AllArgsConstructor
    @Getter
    public static class regionCityInfoResponse<T> {
        private T cityList;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class cityData {
        private String city;
    }
}