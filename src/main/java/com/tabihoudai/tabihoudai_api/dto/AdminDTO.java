package com.tabihoudai.tabihoudai_api.dto;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AdminDTO {

    @AllArgsConstructor
    @Getter
    public static class bannerInfoResponse<T> {
        private T bannerImageList;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class mainBannerData {
        private String path;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class adminBannerList {
        private long bannerIdx;
        private String path;
        private LocalDateTime regDate;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class adminAttrList {
        private long attrIdx;
        private String area;
        private String city;
        private String address;
        private String name;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class adminBlameList {
        private long blameIdx;
        private byte category;
        private String content;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class adminCsList {
        private long csIdx;
        private String nickname;
        private byte type;
        private String content;
    }

    @AllArgsConstructor
    @Getter
    public static class adminPageResponse<DTO> {
        private List<DTO> itemList;
        private int page;
        private int totalPage;
        private int limit; // 목록 사이즈
        private int start, end;
        private boolean prev, next;
        private List<Integer> pageList; // 페이지 번호 목록

        public adminPageResponse(Page<DTO> result) {

            itemList = result.stream().collect(Collectors.toList());
            totalPage = result.getTotalPages();
            makePageList(result.getPageable());
        }

        private void makePageList(Pageable pageable) {
            this.page = pageable.getPageNumber();
            this.limit = pageable.getPageSize();
            int tempEnd = (int) (Math.ceil(page / 10.0)) * 10;
            start = tempEnd - 9;
            prev = start > 1;
            end = totalPage > tempEnd ? tempEnd : totalPage;
            next = totalPage > tempEnd;
            pageList = IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
        }
    }

    @AllArgsConstructor
    @Builder
    @Getter
    public static class adminPageRequestList {
        private int page; // 현재 페이지
        private int size; // 한 페이지에 표시할 데이터 수
        private String searchArea;
        private String searchCity;

        public adminPageRequestList() {
            this.page = 1;
            this.size = 10;
        }

        public Pageable getPageable(Sort sort) {
            return PageRequest.of(page - 1, size, sort);
        }
    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    public static class attrCreateModifyRequestList {
        private long attrIdx;
        private String address;
        private String description;
        private double latitude;
        private double longitude;
        private String attraction;
        private String tag;
        private String area;
        private String city;
        private String thumbnail;
        private String rmImg;
        private List<MultipartFile> images;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class attrModifyDataResponse {
        private long attrIdx;
        private String name;
        private String area;
        private String city;
        private String address;
        private double latitude;
        private double longitude;
        private String description;
        private String tag;
        private List<attrModifyImgDataResponse> attrImgList;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class attrModifyImgDataResponse {
        private long attrImgIdx;
        private String path;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class csViewerResponse {
        private UUID userIdx;
        private LocalDate askDate;
        private String title;
        private String content;
        private String reply;
        private LocalDate replyDate;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class CsReplyRequest {
        private String reply;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class blameViewerResponse {
        private UUID userIdx;
        private Long blameIdx;
        private Long blameContentIdx;
        private String blameContent;
        private byte blameReason;
        private LocalDate date;
    }

    @AllArgsConstructor
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Builder
    @Getter
    public static class userBlockRequest {
        private UUID userIdx;
        private Byte flag;
        private long contentIdx;
    }
}