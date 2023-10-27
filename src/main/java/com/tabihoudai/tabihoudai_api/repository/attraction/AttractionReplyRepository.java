package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface AttractionReplyRepository extends JpaRepository<AttractionReplyEntity, Long> {

    AttractionReplyEntity findByAttrReplyIdx(@Param("attrReplyIdx") Long attrReplyIdx);
}