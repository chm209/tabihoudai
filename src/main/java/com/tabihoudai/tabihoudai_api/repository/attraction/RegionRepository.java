package com.tabihoudai.tabihoudai_api.repository.attraction;


import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RegionRepository extends JpaRepository<RegionEntity, Long> {

    @Query("SELECT r " +
            "FROM RegionEntity r " +
            "WHERE r.regionIdx  = :regionIdx " )
    RegionEntity findAll(@Param("regionIdx") Long regionIdx);
}
