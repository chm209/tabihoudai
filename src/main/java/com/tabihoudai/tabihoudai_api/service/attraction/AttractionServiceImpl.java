package com.tabihoudai.tabihoudai_api.service.attraction;


import com.querydsl.core.Tuple;
import com.tabihoudai.tabihoudai_api.dto.attraction.*;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrImgEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttractionEntity.attractionEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttrReplyEntity.attrReplyEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttrImgEntity.attrImgEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttractionServiceImpl implements AttractionService{

    private final AttractionRepository attractionRepository;

    @Override
    public AttrResultDTO<AttrListDTO, Object[]> getAttrList(AttrRequestDTO attrRequestDTO) {
        int type = attrRequestDTO.getType();
        String word= attrRequestDTO.getKeyword();
        int sort = attrRequestDTO.getSort();
        String so = "";
        String tp = "";
        if(sort==0){
            so="grade";
        } else if (sort==1) {
            so="attraction";
        } else if (sort==2) {
            so="commentCount";
        }
        if(type==0){
            tp="area";
        } else if (type==1) {
            tp="city";
        } else if (type==2) {
            tp="attraction";
        }
        Pageable pageable = attrRequestDTO.getPageable(Sort.by(so).descending());
        Page<Object[]> result = attractionRepository.getAttractionList(pageable,tp,word);

        Function<Object[],AttrListDTO> fn;
        fn=(arr -> entityToDTO(
                (AttractionEntity) arr[0],
                (AttrImgEntity) arr[1],
                (Double) arr[2],
                (Long) arr[3]
        ));

        return new AttrResultDTO<>(result,fn);
    }

    @Override
    public AttrDetailDTO getAttrDetail(Long attrIdx) {
        List<AttractionEntity> list = attractionRepository.getAttractionDetail(attrIdx);
        List<AttrReplyEntity> replyEntityList = attractionRepository.getAttractionReply(attrIdx);
        List<AttrImgEntity> imgList = attractionRepository.getAttractionImg(attrIdx);
        List<Double> attractionAvg = attractionRepository.getAttractionAvg(attrIdx);

        AttractionEntity attraction = list.get(0);
        AttrDetailDTO detailDTO = new AttrDetailDTO();
        List<String> subImgList = new ArrayList<>();
        List<AttrReplyDto> replyList = new ArrayList<>();

        detailDTO.setAttrId(attraction.getAttrIdx());
        detailDTO.setAttraction(attraction.getAttraction());
        detailDTO.setAddress(attraction.getAddress());
        detailDTO.setTag(attraction.getTag());
        detailDTO.setDescription(attraction.getDescription());
        detailDTO.setGrade(attractionAvg.get(0));
        replyEntityList.forEach(arr -> {
            replyList.add(entityToDTOReply(arr));
        });
        imgList.forEach(arr -> {
            if(arr.getType()=='0'){
                detailDTO.setMainImg(arr.getPath());
            } else {
                subImgList.add(arr.getPath());
            }
        });
        detailDTO.setSubImg(subImgList);
        detailDTO.setReply(replyList);

        return detailDTO;
    }
}
