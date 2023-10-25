package com.tabihoudai.tabihoudai_api.dto.attraction;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class AttrRequestDTO {

    private String keyword;
    private int sort;
    private int page;
    private int type;

    public String getKeyword(){
        return this.keyword;
    }

    public AttrRequestDTO(){
        this.page =1;
    }

    public Pageable getPageable(Sort sort){
        return PageRequest.of(page -1,10,sort);
    }


}
