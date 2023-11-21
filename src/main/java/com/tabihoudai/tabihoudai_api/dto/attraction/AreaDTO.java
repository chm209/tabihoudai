package com.tabihoudai.tabihoudai_api.dto.attraction;

import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import lombok.*;

import javax.swing.plaf.synth.Region;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class AreaDTO {

    private String area;

    public RegionEntity areaDtoToEntity() {
        RegionEntity regionEntity = RegionEntity.builder()
                .area(area)
                .build();
        return regionEntity;
    }
}
