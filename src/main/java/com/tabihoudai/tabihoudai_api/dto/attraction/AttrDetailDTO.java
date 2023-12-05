package com.tabihoudai.tabihoudai_api.dto.attraction;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class AttrDetailDTO {

    private Long attrId;
    private String attraction;
    private String mainImg;
    private List<String> subImg = new ArrayList<>();
    private String address;
    private String tag;
    private String description;
    private double grade;


}
