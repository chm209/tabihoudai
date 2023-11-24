package com.tabihoudai.tabihoudai_api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanLikeEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.uuid.UUIDDeserializer;
import lombok.*;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlanLikeDTO {

    private Long planIdx;

    @JsonDeserialize(using = UUIDDeserializer.class)
    private UUID userIdx;

    public PlanLikeEntity planLikeDtoToEntity() {
        UsersEntity usersEntity = UsersEntity.builder()
                .userIdx(userIdx)
                .build();

        PlanEntity planEntity = PlanEntity.builder()
                .planIdx(planIdx)
                .build();

        return PlanLikeEntity.builder()
                .planEntity(planEntity)
                .usersEntity(usersEntity)
                .build();
    }

}
