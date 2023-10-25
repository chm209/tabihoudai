package com.tabihoudai.tabihoudai_api.dto.attraction;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttrDetailDTO {

    private Long attrId;
    private String attraction;
    private String mainImg;
    private String[] subImg;
    private String address;
    private String tag;
    private String description;
    private double grade;

}
