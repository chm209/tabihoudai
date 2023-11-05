package com.tabihoudai.tabihoudai_api.repository.plan;

import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface PlanRepository extends JpaRepository<PlanEntity, Long>, PlanRepositoryCustom {

    PlanEntity findByPlanIdx(@Param("planIdx") Long planIdx);

    @Transactional
    void deleteByPlanIdx(@Param("planIdx") long planIdx);
}