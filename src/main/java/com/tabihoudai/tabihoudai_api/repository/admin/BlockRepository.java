package com.tabihoudai.tabihoudai_api.repository.admin;

import com.tabihoudai.tabihoudai_api.entity.admin.BlameEntity;
import com.tabihoudai.tabihoudai_api.entity.admin.BlockEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface BlockRepository extends JpaRepository<BlockEntity, Long>, BlockRepositoryCustom {

    Page<BlockEntity> findAllByOrderByStartDateDesc(Pageable pageable);

    BlockEntity findByUsersEntity_UserIdx(@Param("userIdx") UUID userIdx);

    @Transactional
    void deleteByBlockIdx(@Param("blockIdx") long blockIdx);
}
