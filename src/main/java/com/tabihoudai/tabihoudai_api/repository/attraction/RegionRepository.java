package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RegionRepository extends JpaRepository<RegionEntity, Long> {

    RegionEntity findByRegionIdx(@Param("regionIdx") Long regionIdx);




    @Query("SELECT r " +
            "FROM RegionEntity r " +
            "WHERE TRUNC(r.regionIdx / 10) = :regionIdx ")
    List<RegionEntity> findCity(@Param("regionIdx") Long regionIdx);

    RegionEntity findRegionEntityByAreaAndCity(@Param("area") String area, @Param("city") String city);
}
