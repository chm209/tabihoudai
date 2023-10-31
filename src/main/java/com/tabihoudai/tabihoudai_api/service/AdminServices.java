package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public interface AdminServices {

    PageResultDTO getAdminManagementList(int item, PageRequestDTO pageRequestDTO);
    String uploadBannerImage(MultipartFile uploadFile);

    // 배너 이미지 관리 리스트
    default AdminDTO.bannerInfo bannerEntityToDto(Object[] bannerEntity) {
        return AdminDTO.bannerInfo.builder()
                        .bannerIdx((Long) bannerEntity[0])
                        .path((String) bannerEntity[1])
                        .regDate((LocalDateTime) bannerEntity[2])
                        .build();
    }

    default AdminDTO.blameInfo blameEntityToDto(Object[] blameEntity) {
        return AdminDTO.blameInfo.builder()
                        .blameIdx((Long) blameEntity[0])
                        .category((byte) blameEntity[1])
                        .content((String) blameEntity[2])
                        .build();
    }

    default AdminDTO.csInfo csEntityToDto(Object[] csEntity) {
        return AdminDTO.csInfo.builder()
                        .csIdx((Long) csEntity[0])
                        .nickname((String) csEntity[1])
                        .type((byte) csEntity[2])
                        .content((String) csEntity[3])
                        .build();
    }

    default AdminDTO.attrInfo attrEntityToDto(Object[] attrEntity) {
        return AdminDTO.attrInfo.builder()
                        .attrIdx((Long) attrEntity[0])
                        .area((String) attrEntity[1])
                        .city((String) attrEntity[2])
                        .address((String) attrEntity[3])
                        .name((String) attrEntity[4])
                        .build();
    }
}