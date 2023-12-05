package com.tabihoudai.tabihoudai_api.dto.attraction;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AttrReplyDto {

    private Long attrReplyIdx;
    private Long attrIdx;
    private byte[] userIdx;
    private String content;
    private LocalDateTime regDate;
    private double score;
    private String path;
    private boolean imageRecycle;

}
