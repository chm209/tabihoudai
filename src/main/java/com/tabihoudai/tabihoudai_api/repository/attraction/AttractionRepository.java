package com.tabihoudai.tabihoudai_api.repository.attraction;

import com.querydsl.core.Tuple;
import com.tabihoudai.tabihoudai_api.dto.attraction.AttrDetailDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.RegionDto;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrImgEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttractionRepository {

    Page<Object[]> getAttractionList(Pageable pageable,String area,String city, String attr,String word);

    List<AttractionEntity> getAttractionDetail(Long attrIdx);

    public List<Double> getAttractionAvg(Long attrIdx);

    List<AttrReplyEntity> getAttractionReply(Long attrIdx);

    List<AttrImgEntity> getAttractionImg(Long attrIdx);

    List<RegionEntity> getRegion();

}
