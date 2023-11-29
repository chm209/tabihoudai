package com.tabihoudai.tabihoudai_api.dto;


import com.fasterxml.jackson.annotation.JsonFormat;
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
public class PlanDTO {

    private long planIdx;

    private int adult;

    private int child;

    private int budget;

    private LocalDateTime regDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.sql.Date dateFrom;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private java.sql.Date dateTo;

    private String title;

    private String content;

    private String attrList;

    private int visitCount;

    @JsonDeserialize(using = UUIDDeserializer.class)
    private UUID userIdx;

    private Long likeCount;

    public PlanEntity planDtoToEntity(){
        UsersEntity usersEntity = UsersEntity.builder()
                .userIdx(userIdx)
                .build();

        return PlanEntity.builder()
                .planIdx(planIdx)
                .adult(adult)
                .child(child)
                .budget(budget)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .title(title)
                .content(content)
                .usersEntity(usersEntity)
                .attrList(attrList)
                .build();
    }

    public static PlanDTO planEntityToDTO(PlanEntity plan, UsersEntity user) {
        return PlanDTO.builder()
                .planIdx(plan.getPlanIdx())
                .dateFrom(plan.getDateFrom())
                .dateTo(plan.getDateTo())
                .attrList(plan.getAttrList())
                .adult(plan.getAdult())
                .child(plan.getChild())
                .budget(plan.getBudget())
                .title(plan.getTitle())
                .content(plan.getContent())
                .regDate(plan.getRegDate())
                .userIdx(user.getUserIdx())
                .visitCount(plan.getVisitCount())
                .build();
    }

}
