package com.tabihoudai.tabihoudai_api.dto.attraction;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttrReplyDto {

    private Long attrReplyIdx;
    private Long attrIdx;
    private Long userIdx;
    private String content;
    private LocalDateTime regDate;
    private double score;
    private String path;

}