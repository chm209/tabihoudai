package com.tabihoudai.tabihoudai_api.dto.attraction;

import lombok.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReplyRequestDTO {
    private Long attrIdx;
    private String sort;
    private boolean asc;
    private int page;


    public Pageable getPageable(Sort sort){
        return PageRequest.of(page -1,3,sort);
    }

}
