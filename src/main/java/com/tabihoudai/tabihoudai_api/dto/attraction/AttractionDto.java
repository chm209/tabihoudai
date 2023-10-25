package com.tabihoudai.tabihoudai_api.dto.attraction;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttractionDto {

    private Long attrIdx;
    private Long regionIdx;
    private String address;
    private String description;
    private double latitude;
    private double longitude;
    private String attraction;
    private String tag;

}
