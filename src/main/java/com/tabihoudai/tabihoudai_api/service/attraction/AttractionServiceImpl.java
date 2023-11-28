package com.tabihoudai.tabihoudai_api.service.attraction;


import com.querydsl.core.Tuple;
import com.tabihoudai.tabihoudai_api.dto.attraction.*;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrImgEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttrReplyEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttrReplyRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttractionEntity.attractionEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttrReplyEntity.attrReplyEntity;
import static com.tabihoudai.tabihoudai_api.entity.attraction.QAttrImgEntity.attrImgEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttractionServiceImpl implements AttractionService{

    private final AttractionRepository attractionRepository;

    private final AttrReplyRepository attrReplyRepository;

    @Override
    public List<String> getAreaList() {
        List<String> list = new ArrayList<>();
        List<RegionEntity> region = attractionRepository.getRegion();
        region.forEach(arr -> {
            list.add(arr.getArea());
        });
        List<String> collect = list.stream().distinct().collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<String> getCityList(String area) {
        List<String> list = new ArrayList<>();
        List<RegionEntity> region = attractionRepository.getRegion();
        region.forEach(arr -> {
            if (arr.getArea().equals(area)){
                list.add(arr.getCity());
            }
        });
        return list;
    }


    @Override
    public AttrResultDTO<AttrListDTO, Object[]> getAttrList(AttrRequestDTO attrRequestDTO) {
        int type = attrRequestDTO.getType();
        String word= attrRequestDTO.getKeyword();
        int sort = attrRequestDTO.getSort();
        String so = "grade";
        String area = null;
        String city = null;
        String attr = null;
        if(sort==0){
            so="grade";
        } else if (sort==1) {
            so="attraction";
        } else if (sort==2) {
            so="commentCount";
        }
        if(type==0){
            area="area";
        } else if (type==1) {
            city="city";
        } else if (type==2) {
            attr="attraction";
        }
        Pageable pageable = attrRequestDTO.getPageable(Sort.by(so).ascending());
        Page<Object[]> result = attractionRepository.getAttractionList(pageable,area,city,attr,word);

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
        List<AttrReplyEntity> replyEntityList = attrReplyRepository.getAttractionReply(attrIdx);
        List<AttrImgEntity> imgList = attractionRepository.getAttractionImg(attrIdx);
        Double attractionAvg = attrReplyRepository.getAttractionAvg(attrIdx);

        AttractionEntity attraction = list.get(0);
        AttrDetailDTO detailDTO = new AttrDetailDTO();
        List<String> subImgList = new ArrayList<>();
        List<AttrReplyDto> replyList = new ArrayList<>();

        detailDTO.setAttrId(attraction.getAttrIdx());
        detailDTO.setAttraction(attraction.getAttraction());
        detailDTO.setAddress(attraction.getAddress());
        detailDTO.setTag(attraction.getTag());
        detailDTO.setDescription(attraction.getDescription());
        detailDTO.setGrade(attractionAvg);
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

    @Override
    @Transactional
    public List<AttrReplyDto> register(AttrReplyDto attrReplyDto,MultipartFile multipartFile) {
        String str = "C:\\Users\\dhses\\tabihoudai\\tabi_front\\src\\assets\\images\\attrReply";
        String folderPath = str.replace("\\", File.separator);
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-m-ss"));
        if(multipartFile!=null) {
            String originalFilename = multipartFile.getOriginalFilename();
            String fileName = date + "_" +
                    originalFilename.substring(originalFilename.lastIndexOf("\\") + 1);
            String saveName = folderPath + File.separator + fileName;
            Path path = Paths.get(saveName);
            try {
                multipartFile.transferTo(path);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            attrReplyDto.setPath(fileName);
        }
        AttrReplyEntity entity = dtoToEntityReply(attrReplyDto);

        List<AttrReplyDto> result = new ArrayList<>();
        try {
            AttrReplyEntity save = attrReplyRepository.save(entity);
            List<AttrReplyEntity> replyEntity = attrReplyRepository.getAttractionReply(attrReplyDto.getAttrIdx());
            for (AttrReplyEntity re: replyEntity) {
                result.add(entityToDTOReply(re));
            }
            return result;
        } catch (Exception e){
            return result;
        }

    }

    @Override
    public List<AttrReplyDto> delete(AttrReplyDto attrReplyDto) {
        List<AttrReplyDto> result = new ArrayList<>();
        try {
            attrReplyRepository.deleteById(attrReplyDto.getAttrReplyIdx());
            List<AttrReplyEntity> replyEntity = attrReplyRepository.getAttractionReply(attrReplyDto.getAttrIdx());
            for (AttrReplyEntity re: replyEntity) {
                result.add(entityToDTOReply(re));
            }
            return result;
        } catch (Exception e){
            return result;
        }

    }


}