package com.tabihoudai.tabihoudai_api.dto;

import com.tabihoudai.tabihoudai_api.entity.plan.PlanReplyEntity;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlanReplyEditDTO {

    private long planReplyIdx;

    private String content;

    public PlanReplyEntity planReplyDtoToEntity(){

        return PlanReplyEntity.builder()
                .planReplyIdx(planReplyIdx)
                .content(content)
                .build();

    }
}
