package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.tabihoudai.tabihoudai_api.entity.attraction.AttrReplyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttrReplyRepository {


    public List<Double> getAttractionAvg(Long attrIdx);

    List<AttrReplyEntity> getAttractionReply(Long attrIdx);

}
