package com.tabihoudai.tabihoudai_api.repository.admin;

import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import org.hibernate.sql.Insert;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface BannerRepository extends JpaRepository<BannerEntity, Long>, BannerRepositoryCustom {
    List<BannerEntity> findAll();

    @Transactional
    void deleteByBannerIdx(@Param("bannerIdx") long bannerIdx);

    BannerEntity findByBannerIdx(@Param("bannerIdx") long bannerIdx);
}