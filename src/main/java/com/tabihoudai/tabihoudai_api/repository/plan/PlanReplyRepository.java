package com.tabihoudai.tabihoudai_api.repository.plan;

import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanLikeEntity;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface PlanReplyRepository extends JpaRepository<PlanReplyEntity, Long> {
    @Transactional
    void deleteByPlanReplyIdx(@Param("planReplyIdx") long planReplyIdx);

    @Transactional
    void deleteByPlanEntity_PlanIdx(@Param("planIdx") long planIdx);
}
