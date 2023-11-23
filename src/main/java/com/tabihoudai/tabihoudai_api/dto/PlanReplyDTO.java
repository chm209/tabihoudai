package com.tabihoudai.tabihoudai_api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.uuid.UUIDDeserializer;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PlanReplyDTO {

    private long planReplyIdx;

    private String content;

    private LocalDateTime regDate;

    @JsonDeserialize(using = UUIDDeserializer.class)
    private UUID userIdx;

    private Long planIdx;

    public PlanReplyEntity planReplyDtoToEntity(){
        UsersEntity usersEntity = UsersEntity.builder()
                .userIdx(userIdx)
                .build();
        PlanEntity planEntity = PlanEntity.builder()
                .planIdx(planIdx)
                .build();

        return PlanReplyEntity.builder()
                .usersEntity(usersEntity)
                .planEntity(planEntity)
                .planReplyIdx(planReplyIdx)
                .regDate(regDate)
                .content(content)
                .build();

    }
}
