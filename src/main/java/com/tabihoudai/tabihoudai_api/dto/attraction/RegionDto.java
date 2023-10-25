package com.tabihoudai.tabihoudai_api.dto.attraction;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegionDto {

    private Long regionIdx;
    private String area;
    private String city;

}
