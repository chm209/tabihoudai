package com.tabihoudai.tabihoudai_api.service;

import com.querydsl.core.Tuple;
import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.dto.AttrCreateRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.PageRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.PageResultDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;
import com.tabihoudai.tabihoudai_api.repository.admin.BannerRepository;
import com.tabihoudai.tabihoudai_api.repository.admin.BlameRepository;
import com.tabihoudai.tabihoudai_api.repository.admin.CsRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionImageRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.AttractionRepository;
import com.tabihoudai.tabihoudai_api.repository.attraction.RegionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminServicesImpl implements AdminServices {

    @Autowired
    private final BannerRepository bannerRepository;

    @Autowired
    private final BlameRepository blameRepository;

    @Autowired
    private final CsRepository csRepository;

    @Autowired
    private final AttractionRepository attractionRepository;

    @Autowired
    private final AttractionImageRepository attractionImageRepository;

    @Autowired
    private final RegionRepository regionRepository;

    @Value("${com.tabihoudai.upload.path}")
    private String uploadPath;


    @Override
    public String patchAttraction(AttrCreateRequestDTO attrCreateRequestDTO) {
        RegionEntity regionEntity = regionRepository.findRegionEntityByAreaAndCity(attrCreateRequestDTO.getArea(), attrCreateRequestDTO.getCity());
        AttractionEntity attrEntity = attrDtoToEntity(attrCreateRequestDTO, regionEntity);
        log.info("0");
        attractionRepository.patchAttraction(attrEntity);
        log.info("1");
        // 이미지 수정 절차
            // 1. 삭제할 이미지대로 먼저 이미지를 삭제하고
            //      -> 이미지를 다 받아온다
            List<AttractionImageEntity> attrImage = attractionImageRepository.findAllByAttrEntityAttrIdx(attrEntity.getAttrIdx());
            //      -> 삭제할 이미지 확인
            String[] split = attrCreateRequestDTO.getRmImg().split(",");
            //      -> 삭제할 이미지 삭제
            for(String str : split) {
                attractionImageRepository.deleteByAttrImgIdx(Long.parseLong(str.replaceAll("[^0-9]", "")));
            }
            log.info("2");
            //      -> 전체 장수 확인
            int attrImgSize = attrImage.size();
            //      -> attrImgIdx 새로 설정
        String attrIdxBase = String.valueOf(attrCreateRequestDTO.getAttrIdx());
        List<AttractionImageEntity> allByAttrEntityAttrIdx = attractionImageRepository.findAllByAttrEntityAttrIdx(attrCreateRequestDTO.getAttrIdx());

        for(int k = 0 ; k < attrImage.size() ; k++) {
            attractionRepository.patchAttrImgIdx(attrImage.get(k), attrCreateRequestDTO.getAttrIdx(), Long.parseLong(attrIdxBase.concat(String.valueOf(k+1))));
        }
        log.info("3");

        // 2. 새로 받은 이미지 추가
        out:for(MultipartFile m : attrCreateRequestDTO.getImages()) {
            if (m.getContentType().startsWith("image") == false) {
                break out;
            }
            log.info("4");
            String attr = String.valueOf(attrEntity.getAttrIdx());
            String fileName = m.getOriginalFilename();
            String folderPath = makeForder(1);
            String saveName = uploadPath + File.separator + folderPath + File.separator + fileName;
            Path path = Paths.get(saveName);
            try {
                m.transferTo(path);
            }
            catch (IOException e) { }
            log.info("5");
            AttractionImageEntity attractionImageEntity = attrImgDtoToEntity(path, attrCreateRequestDTO.getThumbnail(), attrEntity, m, Long.valueOf(attrIdxBase.concat(String.valueOf(attrImgSize++))));
            log.info("6");
            attractionImageRepository.save(attractionImageEntity);
        }
        log.info("7");
        return "success";
    }

    @Override
    public PageResultDTO getAdminManagementList(int item, PageRequestDTO pageRequestDTO) {
        if(item == 1) { // 배너 이미지
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("bannerIdx").descending());
            Page<Object[]> list = bannerRepository.getBannerPage(pageRequest);
            Page<AdminDTO.bannerInfo> result = list.map(objects -> bannerEntityToDto(objects));
            return new PageResultDTO<>(result);
        }
        else if(item == 2) { // 관광 명소 관리
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("attrIdx").ascending());
            Long regionIdx = Long.parseLong(pageRequestDTO.getSearchArea()) + Long.parseLong(pageRequestDTO.getSearchCity());
            String[] region = (regionRepository.getAreaCity(regionIdx)).split(",");
            Page<Object[]> list = attractionRepository.getAttractionPage(region[0], region[1], pageRequest);
            Page<AdminDTO.attrInfo> result = list.map(objects -> attrEntityToDto(objects));
            return new PageResultDTO<>(result);
        }
        else if(item == 3) { // 신고 관리
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("blameIdx").descending());
            Page<Object[]> list = blameRepository.getBlamePage(pageRequest);
            Page<AdminDTO.attrInfo> result = list.map(objects -> attrEntityToDto(objects));
            return new PageResultDTO<>(result);
        }
        else { // 문의 관리
            PageRequest pageRequest = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize(), Sort.by("csIdx").descending());
            Page<Object[]> list = csRepository.getCsPage(pageRequest);
            Page<AdminDTO.csInfo> result = list.map(objects -> csEntityToDto(objects));
            return new PageResultDTO<>(result);
        }
    }

    @Override
    public AdminDTO.attrDetailData getAttrDetailData(long attrIdx) {
        List<AttractionImageEntity> attrImageResult = attractionImageRepository.findAllByAttrEntityAttrIdx(attrIdx);
        return attrDetailEntityToDto(attrImageResult);
    }

    @Override
    public String uploadBannerImage(MultipartFile uploadFile) {
        if (uploadFile.getContentType().startsWith("image") == false) {
            return "fail";
        }
        String fileName = uploadFile.getOriginalFilename();
        String folderPath = makeForder(0);
        String saveName = uploadPath + File.separator + folderPath + File.separator + fileName;
        Path path = Paths.get(saveName);
        try {
            uploadFile.transferTo(path);
            bannerRepository.save(BannerEntity.builder().path(saveName).build());
        }
        catch (IOException e) { }
        return "success";
    }

    private String makeForder(int func) {
        if(func == 0) {
            File uploadPathFolder = new File(uploadPath, "banner");
            if (!uploadPathFolder.exists()) {
                uploadPathFolder.mkdirs();
            }
            return "banner";
        }
        else {
            File uploadPathFolder = new File(uploadPath, "1633");
            if (!uploadPathFolder.exists()) {
                uploadPathFolder.mkdirs();
            }
            return "1633";
        }
    }
}
