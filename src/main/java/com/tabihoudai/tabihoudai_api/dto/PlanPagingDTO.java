package com.tabihoudai.tabihoudai_api.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
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
public class PlanPagingDTO {

    private Long planIdx;

    private LocalDateTime regDate;

    private String title;

    private int visitCount;

    @JsonDeserialize(using = UUIDDeserializer.class)
    private UUID userIdx;

    public PlanEntity planPagingDtoToEntity(){
        UsersEntity usersEntity = UsersEntity.builder()
                .userIdx(userIdx)
                .build();

        return PlanEntity.builder()
                .planIdx(planIdx)
                .regDate(regDate)
                .title(title)
                .visitCount(visitCount)
                .usersEntity(usersEntity)
                .build();

    }

}
