package com.tabihoudai.tabihoudai_api.dto.board;

import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

    @Builder
    @AllArgsConstructor
    @Getter
    @Setter
    @ToString
    public static class PageRequestDTO {
        //@Schema(description = "페이지 번호",requiredMode = Schema.RequiredMode.REQUIRED)
        private int page;
        //@Schema(description = "페이지 사이즈",requiredMode = Schema.RequiredMode.REQUIRED)
        private int size = 10;
        //@Schema(description = "검색 타입")
        private String type;
        //@Schema(description = "검색 키워드")
        private String keyword;
        public PageRequestDTO(){
            this.page = 1;
            this.size = 10;
        }

        //@Schema(hidden = true)
        public Pageable getPageable(Sort sort){
            return PageRequest.of(page-1, size, sort);
        }

        //@Schema(hidden = true)
        public Pageable getPageable(){
            return PageRequest.of(page-1,size);
        }
    }

    @Getter
    @Setter
    @ToString
    public static class PageResultDTO<DTO, EN> {

        private List<DTO> dtoList;
        private int totalPage;
        private int page;
        private int size;

        private int start, end;
        private boolean prev, next;

        private List<Integer> pageList;

        public PageResultDTO(Page<EN> result, Function<EN,DTO> fn){
            dtoList = result.stream().map(fn).collect(Collectors.toList());
            totalPage = result.getTotalPages();

            makePageList(result.getPageable());
        }

        private void makePageList(Pageable pageable) {
            this.page = pageable.getPageNumber() + 1;
            this.size = pageable.getPageSize();
            int tempEnd = (int)(Math.ceil(page/10.0)) * 10;
            start = tempEnd - 9;
            prev = start > 1;
            end = totalPage > tempEnd ? tempEnd : totalPage;
            next = totalPage > tempEnd;
            pageList = IntStream.rangeClosed(start, end).boxed().toList();

        }
    }
}
