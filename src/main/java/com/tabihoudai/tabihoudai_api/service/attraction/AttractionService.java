package com.tabihoudai.tabihoudai_api.service.attraction;


import com.tabihoudai.tabihoudai_api.dto.attraction.AttrListDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.AttrRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.AttrResultDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.AttractionDto;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrImgEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


public interface AttractionService {
    AttrResultDTO<AttrListDTO,Object[]> getAttrList(AttrRequestDTO attrRequestDTO);

    default AttrListDTO entityToDTO(AttractionEntity attractionEntity, AttrImgEntity attrImgEntity, double avg, Long count ){
        AttrListDTO attrListDTO = AttrListDTO.builder().attrId(attractionEntity.getAttrIdx())
                .attraction(attractionEntity.getAttraction())
                .img(attrImgEntity.getPath())
                .tag(attractionEntity.getTag())
                .latitude(attractionEntity.getLatitude())
                .longitude(attractionEntity.getLongitude())
                .build();
        attrListDTO.setGrade(avg);
        attrListDTO.setCommentCount(count);
        return attrListDTO;
    }
}
