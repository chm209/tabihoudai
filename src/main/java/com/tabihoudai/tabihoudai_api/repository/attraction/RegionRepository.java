package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.tabihoudai.tabihoudai_api.dto.attraction.AreaDTO;
import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public interface RegionRepository extends JpaRepository<RegionEntity, Long> {

    @Query("SELECT r " +
            "FROM RegionEntity r " +
            "WHERE r.regionIdx  = :regionIdx " )
    RegionEntity findAll(@Param("regionIdx") Long regionIdx);


    @Query("SELECT r.area FROM RegionEntity r GROUP BY r.area")
    ArrayList<String> findArea();

    @Query("SELECT r.city FROM RegionEntity r WHERE r.area = :city")
    ArrayList<String> findCity(@Param("city") String city);

    @Query("SELECT r.regionIdx FROM RegionEntity r WHERE r.city = :city")
    Long findRegionIdx(@Param("city") String city);

}
