package com.tabihoudai.tabihoudai_api.service.attraction;

import com.tabihoudai.tabihoudai_api.dto.attraction.AttrReplyDto;
import com.tabihoudai.tabihoudai_api.dto.attraction.AttrRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.ReplyListDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.ReplySearchDTO;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.UserEntity;
import org.springframework.data.domain.Slice;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface AttrReplyService {

    List<ReplySearchDTO> register(AttrReplyDto attrReplyDto, MultipartFile multipartFile);

    List<ReplySearchDTO> delete(AttrReplyDto attrReplyDto);

    ReplyListDTO getReply(Long attrIdx, int page, int size);


    default AttrReplyEntity dtoToEntityReply(AttrReplyDto attrReplyDto) {
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


    default AttrReplyDto entityToDTOReply(AttrReplyEntity attrReplyEntity) {
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

    default ReplySearchDTO reEntityToReplyDto(AttrReplyEntity attrReplyEntity,String email){
        ReplySearchDTO replySearchDTO= ReplySearchDTO.builder()
                .attrReplyIdx(attrReplyEntity.getAttrReplyIdx())
                .userIdx(attrReplyEntity.getUserIdx().getUserIdx())
                .email(email)
                .content(attrReplyEntity.getContent())
                .regDate(attrReplyEntity.getRegDate())
                .score(attrReplyEntity.getScore())
                .path(attrReplyEntity.getPath()).build();
        return replySearchDTO;
    }

}
