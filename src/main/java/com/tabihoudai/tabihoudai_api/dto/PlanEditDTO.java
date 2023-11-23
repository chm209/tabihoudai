package com.tabihoudai.tabihoudai_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import com.tabihoudai.tabihoudai_api.uuid.UUIDDeserializer;
import lombok.*;

import java.util.UUID;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PlanEditDTO {

    private long planIdx;

    private int adult;

    private int child;

    private int budget;

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

    public PlanEntity planEditDtoToEntity(){
        UsersEntity usersEntity = UsersEntity.builder()
                .userIdx(userIdx)
                .build();

        return PlanEntity.builder()
                .planIdx(planIdx)
                .usersEntity(usersEntity)
                .adult(adult)
                .child(child)
                .budget(budget)
                .dateFrom(dateFrom)
                .dateTo(dateTo)
                .title(title)
                .content(content)
                .attrList(attrList)
                .build();
    }

}
