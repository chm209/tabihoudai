package com.tabihoudai.tabihoudai_api.repository.admin;

import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.entity.admin.CsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface CsRepository extends JpaRepository<CsEntity, Long> {

    Page<CsEntity> findAllByOrderByAskDateDesc(Pageable pageable);

    @Query("SELECT new map(c.askDate as regDate, c.title as content, c.cs_idx as id) FROM CsEntity c WHERE c.usersEntity.userIdx = :userIdx AND ROWNUM <= 4 ORDER BY id DESC")
    List<Map<String, Object>> findByUserIdx(@Param("userIdx") UUID userIdx);
}
