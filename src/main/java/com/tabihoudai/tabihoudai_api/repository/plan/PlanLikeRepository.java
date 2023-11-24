package com.tabihoudai.tabihoudai_api.repository.plan;

import com.tabihoudai.tabihoudai_api.entity.plan.PlanLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlanLikeRepository extends JpaRepository<PlanLikeEntity, Long> {

    @Query("SELECT COUNT(*) FROM PlanLikeEntity p WHERE p.planEntity = :#{#planLikeEntity.planEntity}")
    Long chkLike(@Param("planLikeEntity") PlanLikeEntity planLikeEntity);

    @Modifying
    @Query("DELETE FROM PlanLikeEntity p WHERE p.usersEntity = :#{#planLikeEntity.usersEntity} AND p.planEntity = :#{#planLikeEntity.planEntity}")
    void disLike(@Param("planLikeEntity") PlanLikeEntity planLikeEntity);

}
