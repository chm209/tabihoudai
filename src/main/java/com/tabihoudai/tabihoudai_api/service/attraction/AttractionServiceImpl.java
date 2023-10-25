package com.tabihoudai.tabihoudai_api.service.attraction;


import com.tabihoudai.tabihoudai_api.dto.attraction.AttrListDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.AttrRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.attraction.AttrResultDTO;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrImgEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
}
