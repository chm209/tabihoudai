package com.tabihoudai.tabihoudai_api.dto.attraction;

import lombok.*;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReplySearchDTO {

    private Long attrReplyIdx;
    private byte[] userIdx;
    private String userImage;
    private String email;
    private String content;
    private LocalDateTime regDate;
    private double score;
    private String path;

}
