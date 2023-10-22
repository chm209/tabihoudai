package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;

import java.util.List;

public interface HomeServices {

    List<AdminDTO.bannerInfo> getBanner();

    // 기능 추가하면 수정할 필요 있음
    default AdminDTO.bannerInfo entityToDto(BannerEntity banner) {
        return AdminDTO.bannerInfo.builder()
                .banner_idx(banner.getBannerIdx())
                .path(banner.getPath())
                .regDate(banner.getRegDate())
                .build();
    }
}
