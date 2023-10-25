package com.tabihoudai.tabihoudai_api.dto.attraction;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttrImgDto {

    private Long attrImgIdx;
    private Long attrIdx;
    private String path;
    private String type;

}
