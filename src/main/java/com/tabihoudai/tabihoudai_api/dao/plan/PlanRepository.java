package com.tabihoudai.tabihoudai_api.dao.plan;

import com.tabihoudai.tabihoudai_api.dto.plan.PlanDTO;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PlanRepository extends JpaRepository<PlanEntity, Long> {

    //네이티브 쿼리 떼기
    @Query(value = "SELECT * " +
            "FROM PLAN " +
            "WHERE PLAN_IDX = :planIdx " , nativeQuery = true)
    List<PlanEntity> planView(@Param("planIdx") Long planIdx);

    //명소이미지 레포지토리로 옮기기, 네이티브 쿼리 떼기
    @Query(value = "SELECT PATH FROM ATTR_IMG WHERE ATTR_IDX = :attrIdx AND ROWNUM = 1", nativeQuery = true)
    String planAttrImage(@Param("attrIdx") Long attrIdx);

    @Query("SELECT new map(p.regDate as regDate, p.title as content, p.planIdx as id) FROM PlanEntity p WHERE p.usersEntity.userIdx = :userIdx AND ROWNUM <= 4 ORDER BY id DESC")
    List<Map<String, Object>> findByUserIdx(@Param("userIdx") UUID userIdx);



    default PlanDTO planEntityToDTO(PlanEntity plan, UsersEntity user) {
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
