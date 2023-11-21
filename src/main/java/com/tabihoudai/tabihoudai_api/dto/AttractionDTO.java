package com.tabihoudai.tabihoudai_api.dto;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttractionDTO {

    private long attrIdx;

    private String address;

    private String description;

    private double latitude;

    private double longitude;

    private String attraction;

    private String tag;




}
