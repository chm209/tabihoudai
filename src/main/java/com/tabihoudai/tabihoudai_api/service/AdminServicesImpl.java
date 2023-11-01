package com.tabihoudai.tabihoudai_api.service;

import com.querydsl.core.Tuple;
import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.dto.PageRequestDTO;
import com.tabihoudai.tabihoudai_api.dto.PageResultDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
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
        String folderPath = makeForder();
        String saveName = uploadPath + File.separator + folderPath + File.separator + fileName;
        Path path = Paths.get(saveName);
        try {
            uploadFile.transferTo(path);
            bannerRepository.save(BannerEntity.builder().path(saveName).build());
        }
        catch (IOException e) { }
        return "success";
    }

    private String makeForder() {
        File uploadPathFolder = new File(uploadPath, "banner");
        if (!uploadPathFolder.exists()) {
            uploadPathFolder.mkdirs();
        }
        return "banner";
    }
}
