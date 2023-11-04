package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.List;

public interface AttractionImageRepository extends JpaRepository<AttractionImageEntity, Long> {
    @Query("SELECT A.attrIdx, A.address, A.attraction, A.description, A.latitude, A.longitude, A.tag, A.regionEntity.regionIdx, " +
            "I.path " +
            "FROM AttractionEntity A " +
            "INNER JOIN AttractionImageEntity I ON " +
            "A.attrIdx = I.attrEntity.attrIdx " +
            "AND I.type = '1'" +
            "WHERE A.regionEntity.regionIdx = :regionIdx ")
    List<Object[]> findAllByRegionEntity_RegionIdx_city(@Param("regionIdx") Long regionIdx);

    @Query("SELECT AI " +
            "FROM AttractionImageEntity AI " +
            "WHERE AI.attrEntity.attrIdx = :attrIdx " +
            "AND AI.type = '1'  ")
    AttractionImageEntity getInfo(@Param("attrIdx") Long attrIdx);

    List<AttractionImageEntity> findAllByAttrEntityAttrIdx(@Param("attrIdx") Long attrIdx);

    @Transactional
    void deleteByAttrImgIdx(@Param("attrImgIdx") Long attrImgIdx);

    AttractionImageEntity findByAttrImgIdx(@Param("attrImgIdx") Long attrImgIdx);
    AttractionImageEntity findByPath(@Param("path") String path);
}
