package com.tabihoudai.tabihoudai_api.repository.admin;

import com.tabihoudai.tabihoudai_api.entity.admin.BlameEntity;
import com.tabihoudai.tabihoudai_api.entity.admin.CsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BlameRepository extends JpaRepository<BlameEntity, Long>, BlameRepositoryCustom {
    Page<BlameEntity> findAllByOrderByBlameDateDesc(Pageable pageable);
    @Transactional
    void deleteByBlameIdx(@Param("blameIdx") long blameIdx);
    BlameEntity findByBlameIdx(@Param("blameIdx") long blameIdx);
}
