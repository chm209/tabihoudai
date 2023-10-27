package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttractionRepository extends JpaRepository<AttractionEntity, Long>, AttractionRepositoryCustom {

    @Query("SELECT A.attrIdx, A.address, A.attraction, A.description, A.latitude, A.longitude, A.tag, A.regionEntity.regionIdx, " +
            "I.path " +
            "FROM AttractionEntity A "+
            "INNER JOIN AttractionImageEntity I ON " +
            "A.attrIdx = I.attrEntity.attrIdx " +
            "AND I.type = '1'" +
            "WHERE TRUNC(A.regionEntity.regionIdx / 10) = :regionIdx ")
    List<Object[]> findAllByRegionEntity_RegionIdx_area(@Param("regionIdx") Long regionIdx);

    @Query("SELECT A.attrIdx, A.address, A.attraction, A.description, A.latitude, A.longitude, A.tag, A.regionEntity.regionIdx, " +
            "I.path " +
            "FROM AttractionEntity A "+
            "INNER JOIN AttractionImageEntity I ON " +
            "A.attrIdx = I.attrEntity.attrIdx " +
            "AND I.type = '1'" +
            "WHERE A.regionEntity.regionIdx = :regionIdx ")
    List<Object[]> findAllByRegionEntity_RegionIdx_city(@Param("regionIdx") Long regionIdx);

    AttractionEntity findByAttrIdx(@Param("attrIdx") Long attrIdx);

    @Query("SELECT A " +
            "FROM AttractionEntity A " +
            "WHERE A.regionEntity.regionIdx = :regionIdx")
    List<AttractionEntity> getAttractionList(@Param("regionIdx") Long regionIdx);
}