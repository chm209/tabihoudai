package com.tabihoudai.tabihoudai_api.dto.board;
//게시글 리스트 할때 중요함

//import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

//@Schema(description = "방명록 요청 DTO")
@Builder
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PageRequestDTO {
    //@Schema(description = "페이지 번호",requiredMode = Schema.RequiredMode.REQUIRED)
    private int page;
    //@Schema(description = "페이지 사이즈",requiredMode = Schema.RequiredMode.REQUIRED)
    private int size;
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
