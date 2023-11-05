package com.tabihoudai.tabihoudai_api.repository.plan;

import com.tabihoudai.tabihoudai_api.entity.plan.PlanLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PlanLikeRepository extends JpaRepository<PlanLikeEntity, Long> {
    @Transactional
    void deleteByPlanEntity_PlanIdx(@Param("planIdx") long planIdx);
}
