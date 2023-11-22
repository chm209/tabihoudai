package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttractionRepository extends JpaRepository<AttractionEntity, Long> {

    @Query("SELECT A " +
            "FROM AttractionEntity A " +
            "WHERE A.regionEntity.regionIdx = :regionIdx")
    List<AttractionEntity> getAttractionList(@Param("regionIdx") Long regionIdx);

    @Query(value = "SELECT A.attrIdx, A.attraction FROM AttractionEntity A WHERE A.regionEntity.regionIdx = :city")
    List<Object[]> getAttractionListByRegionIdx(@Param("city") Long city);


}
