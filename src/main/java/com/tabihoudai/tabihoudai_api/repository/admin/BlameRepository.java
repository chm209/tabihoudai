package com.tabihoudai.tabihoudai_api.repository.admin;

import com.tabihoudai.tabihoudai_api.entity.admin.BlameEntity;
import com.tabihoudai.tabihoudai_api.entity.admin.CsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlameRepository extends JpaRepository<BlameEntity, Long> {
    Page<BlameEntity> findAllByOrderByBlameDateDesc(Pageable pageable);
}
