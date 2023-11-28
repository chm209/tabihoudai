package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.tabihoudai.tabihoudai_api.entity.attraction.AttrReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AttrReplyRepository extends JpaRepository<AttrReplyEntity,Long>{

    @Query(value = "SELECT nvl(avg(ar.score),0.0) FROM tbl_attr_reply ar WHERE ar.attr_Idx = :attrIdx", nativeQuery = true)
    Double getAttractionAvg(@Param("attrIdx") Long attrIdx);

    @Query("SELECT ar FROM AttrReplyEntity ar WHERE ar.attrIdx.attrIdx = :attrIdx")
    List<AttrReplyEntity> getAttractionReply(@Param("attrIdx") Long attrIdx);


    Slice<AttrReplyEntity> findByAttrIdx(AttractionEntity attrIdx, Pageable pageable);




}
