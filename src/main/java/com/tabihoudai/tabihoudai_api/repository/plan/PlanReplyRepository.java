package com.tabihoudai.tabihoudai_api.repository.plan;

import com.tabihoudai.tabihoudai_api.dto.PlanReplyDTO;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanEntity;
import com.tabihoudai.tabihoudai_api.entity.plan.PlanReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.users.UsersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface PlanReplyRepository extends JpaRepository<PlanReplyEntity, Long> {

    @Query("SELECT new map(p.regDate as regDate, p.content as content, p.planReplyIdx as id) " +
            "FROM PlanReplyEntity p WHERE p.usersEntity.userIdx = :userIdx AND ROWNUM <= 4 ORDER BY id DESC")
    List<Map<String, Object>> findByUserIdx(@Param("userIdx") UUID userIdx);

    @Query("SELECT p FROM PlanReplyEntity p WHERE p.planEntity.planIdx = :planIdx")
    Page<PlanReplyEntity> replyView(Pageable pageable, @Param("planIdx") Long planIdx);

    @Modifying
    @Query("UPDATE PlanReplyEntity p SET p.content = :#{#planReplyEntity.content} WHERE p.planReplyIdx = :#{#planReplyEntity.planReplyIdx}")
    void replyEdit(@Param("planReplyEntity") PlanReplyEntity planReplyEntity);


    @Query("DELETE FROM PlanReplyEntity p WHERE p.planReplyIdx = :planIdx")
    void deleteByPlanReplyIdx(@Param("planIdx")long planIdx);

    default PlanReplyDTO planReplyEntityToDTO(PlanReplyEntity reply, UsersEntity user, PlanEntity plan) {
        return PlanReplyDTO.builder()
                .userIdx(user.getUserIdx())
                .planIdx(plan.getPlanIdx())
                .planReplyIdx(reply.getPlanReplyIdx())
                .content(reply.getContent())
                .regDate(reply.getRegDate())
                .build();
    }
}
