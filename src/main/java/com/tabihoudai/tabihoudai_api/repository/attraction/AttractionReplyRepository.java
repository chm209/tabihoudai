package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttractionReplyRepository extends JpaRepository<AttractionReplyEntity, Long> {
    AttractionReplyEntity findByAttrReplyIdx(long AttrReplyIdx);
}
