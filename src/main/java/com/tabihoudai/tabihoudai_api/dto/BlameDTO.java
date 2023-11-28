package com.tabihoudai.tabihoudai_api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tabihoudai.tabihoudai_api.entity.admin.BlameEntity;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.uuid.UUIDDeserializer;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@Getter
@Setter
public class BlameDTO {

    private long blameIdx;

    private LocalDateTime blameDate;

    private byte category;

    private String content;

    @JsonDeserialize(using = UUIDDeserializer.class)
    private UUID userIdx;

    private Long planIdx;

    private Long planReplyIdx;

    public BlameEntity blameDtoTOEntity(byte category){
        UsersEntity usersEntity = UsersEntity.builder()
                .userIdx(userIdx)
                .build();
        if(category == 3){
            PlanEntity planEntity = PlanEntity.builder()
                    .planIdx(planIdx)
                    .build();
            return BlameEntity.builder()
                    .category(category)
                    .blameDate(blameDate)
                    .content(content)
                    .usersEntity(usersEntity)
                    .planEntity(planEntity)
                    .build();
        } else {
            PlanReplyEntity planReplyEntity = PlanReplyEntity.builder()
                    .planReplyIdx(planReplyIdx)
                    .build();
            return BlameEntity.builder()
                    .category(category)
                    .content(content)
                    .usersEntity(usersEntity)
                    .planReplyEntity(planReplyEntity)
                    .build();
        }
    }



}
