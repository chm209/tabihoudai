package com.tabihoudai.tabihoudai_api.dto.attraction;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttrListDTO {

    private Long attrId;
    private String attraction;
    private String img;
    private String tag;
    private double latitude;
    private double longitude;
    private double grade;
    private Long commentCount;

}
