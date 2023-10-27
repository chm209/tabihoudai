package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.*;

import java.time.LocalDateTime;

public interface AdminServices {

    PageResultDTO getAdminManagementList(int item, PageRequestDTO pageRequestDTO);

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
}