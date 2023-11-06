package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.nio.file.Path;
import java.util.List;

public interface AttractionImageRepository extends JpaRepository<AttractionImageEntity, Long> {
    List<AttractionImageEntity> findByAttrEntity_AttrIdx(@Param("attrImgIdx") Long attrImgIdx);

    List<AttractionImageEntity> findAllByAttrEntityAttrIdx(@Param("attrIdx") Long attrIdx);
    AttractionImageEntity findByAttrEntity_AttrIdxAndType(@Param("attrIdx") Long attrIdx, @Param("type") char type);
    @Transactional
    void deleteByAttrImgIdx(@Param("attrImgIdx") Long attrImgIdx);
    AttractionImageEntity findByAttrImgIdx(@Param("attrImgIdx") Long attrImgIdx);
    AttractionImageEntity findByPath(@Param("path") String path);
    @Transactional
    void deleteByAttrEntity_AttrIdx(@Param("attrIdx") long attrIdx);
}
