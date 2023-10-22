package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.dto.AttractionDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;

import java.util.List;

public interface HomeServices {

    List<AdminDTO.bannerInfo> getBanner();
    List<AttractionDTO.attrPreview> getAttraction(int area, Integer city);

    default AdminDTO.bannerInfo entityToDto(BannerEntity banner) {
        return AdminDTO.bannerInfo.builder()
                .banner_idx(banner.getBannerIdx())
                .path(banner.getPath())
                .regDate(banner.getRegDate())
                .build();
    }

    default AttractionDTO.attrPreview entityToDto(Object[] objects) {
        return AttractionDTO.attrPreview.builder()
                    .attr_idx(Long.parseLong(String.valueOf(objects[0])))
                    .address(String.valueOf(objects[1]))
                    .attraction(String.valueOf(objects[2]))
                    .description(String.valueOf(objects[3]))
                    .latitude((Double) objects[4])
                    .longitude((Double) objects[5])
                    .tag(String.valueOf(objects[6]))
                    .region_idx(Long.parseLong(String.valueOf(objects[7])))
                    .path(String.valueOf(objects[8]))
                    .build();
    }
}
