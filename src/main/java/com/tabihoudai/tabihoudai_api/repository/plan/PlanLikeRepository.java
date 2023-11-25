package com.tabihoudai.tabihoudai_api.repository.plan;

import com.tabihoudai.tabihoudai_api.entity.plan.PlanLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PlanLikeRepository extends JpaRepository<PlanLikeEntity, Long> {

    @Query("SELECT COUNT(*) FROM PlanLikeEntity p WHERE p.planEntity = :#{#planLikeEntity.planEntity}")
    Long addLike(@Param("planLikeEntity") PlanLikeEntity planLikeEntity);

    @Query("SELECT p.usersEntity.userIdx " +
            "FROM PlanLikeEntity p " +
            "WHERE p.planEntity.planIdx = :#{#planLikeEntity.planEntity.planIdx} " +
            "AND p.usersEntity.userIdx = :#{#planLikeEntity.usersEntity.userIdx}")
    List<UUID> chkLiked(@Param("planLikeEntity") PlanLikeEntity planLikeEntity);

    @Modifying
    @Query("DELETE FROM PlanLikeEntity p WHERE p.usersEntity = :#{#planLikeEntity.usersEntity} " +
            "AND p.planEntity = :#{#planLikeEntity.planEntity}")
    void disLike(@Param("planLikeEntity") PlanLikeEntity planLikeEntity);

}
