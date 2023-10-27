package com.tabihoudai.tabihoudai_api.repository.admin;

import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.entity.admin.CsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CsRepository extends JpaRepository<CsEntity, Long>, CsRepositoryCustom {

    Page<CsEntity> findAllByOrderByAskDateDesc(Pageable pageable);
}
