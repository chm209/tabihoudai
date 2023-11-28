package com.tabihoudai.tabihoudai_api.service.attraction;


import com.tabihoudai.tabihoudai_api.dto.attraction.*;
import com.tabihoudai.tabihoudai_api.entity.attraction.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


public interface AttractionService {

    List<String> getAreaList();
    List<String> getCityList(String area);
    AttrResultDTO<AttrListDTO,Object[]> getAttrList(AttrRequestDTO attrRequestDTO);
    AttrDetailDTO getAttrDetail(Long attrIdx);

    List<AttrReplyDto> register (AttrReplyDto attrReplyDto, MultipartFile multipartFile);

    List<AttrReplyDto> delete (AttrReplyDto attrReplyDto);


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

    default AttrReplyDto entityToDTOReply(AttrReplyEntity attrReplyEntity){
        AttrReplyDto attrReplyDto = AttrReplyDto.builder()
                .attrReplyIdx(attrReplyEntity.getAttrReplyIdx())
                .attrIdx(attrReplyEntity.getAttrIdx().getAttrIdx())
                .userIdx(attrReplyEntity.getUserIdx().getUserIdx())
                .content(attrReplyEntity.getContent())
                .regDate(attrReplyEntity.getRegDate())
                .score(attrReplyEntity.getScore())
                .path(attrReplyEntity.getPath())
                .build();
        return attrReplyDto;
    }

    default AttrImgDto entityToDTOImg(AttrImgEntity attrImgEntity){
        AttrImgDto attrImgDto = AttrImgDto.builder()
                .attrImgIdx(attrImgEntity.getAttrImgIdx())
                .attrIdx(attrImgEntity.getAttrIdx().getAttrIdx())
                .path(attrImgEntity.getPath())
                .type(attrImgEntity.getType())
                .build();

        return attrImgDto;
    }

    default AttrReplyEntity dtoToEntityReply(AttrReplyDto attrReplyDto){
        AttractionEntity attractionEntity = AttractionEntity.builder().attrIdx(attrReplyDto.getAttrIdx()).build();
        UserEntity userEntity = UserEntity.builder().userIdx(attrReplyDto.getUserIdx()).build();
        AttrReplyEntity entity = AttrReplyEntity.builder()
                .attrReplyIdx(attrReplyDto.getAttrReplyIdx())
                .attrIdx(attractionEntity)
                .userIdx(userEntity)
                .content(attrReplyDto.getContent())
                .score(attrReplyDto.getScore())
                .path(attrReplyDto.getPath())
                .build();

        return entity;
    }


}
