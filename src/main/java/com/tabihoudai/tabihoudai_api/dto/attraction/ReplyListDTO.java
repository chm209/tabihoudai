package com.tabihoudai.tabihoudai_api.dto.attraction;

import lombok.*;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReplyListDTO {

    private List<ReplySearchDTO> list;
    private boolean next;

}
