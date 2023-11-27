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

    private Long likeCount;

    //계획 번호, 제목, 조회수, 작성자, 작성일, 추천수 순
    public static PlanPagingDTO arrayToDTO(Object[] data){
        Long planIdx = (Long) data[0];
        String title = (String) data[1];
        int visitCount = (int) data[2];
        UUID userIdx = (UUID) data[3];
        LocalDateTime regDate = (LocalDateTime) data[4];
        Long likeCount = (Long) data[5];

        return PlanPagingDTO.builder()
                .planIdx(planIdx)
                .regDate(regDate)
                .title(title)
                .visitCount(visitCount)
                .userIdx(userIdx)
                .likeCount(likeCount)
                .build();
    }

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
