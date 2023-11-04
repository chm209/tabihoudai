package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.dto.AttractionDTO;
import com.tabihoudai.tabihoudai_api.dto.BoardDTO;
import com.tabihoudai.tabihoudai_api.dto.PlanDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;

import java.sql.Date;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public interface HomeServices {
    List<AdminDTO.bannerInfo> getBanner();
    List<AttractionDTO.attrPreview> getAttraction(int area, Integer city);
    List<BoardDTO.recentBoard> getBoard();
    List<PlanDTO.bestPlan> getBestPlan(Integer area);
    AttractionDTO.resultCity<AttractionDTO.cityList> getCityList(Integer area);

    default String planImage(Object[] objects) {
        return String.valueOf(objects[4]);
    }

    default Integer areaFactory(Integer area) {
        return Integer.valueOf(area) / 10;
    }

    default AdminDTO.bannerInfo entityToDto(BannerEntity banner) {
        return AdminDTO.bannerInfo.builder()
                .bannerIdx(banner.getBannerIdx())
                .path(banner.getPath())
                .regDate(banner.getRegDate())
                .build();
    }

    default AttractionDTO.cityList regionEntityToDto(RegionEntity regions) {
        return AttractionDTO.cityList.builder().city(regions.getCity()).build();
    }

    default AttractionDTO.attrPreview entityToDto(Object[] objects) {
        return AttractionDTO.attrPreview.builder()
                    .attrIdx(Long.parseLong(String.valueOf(objects[0])))
                    .address(String.valueOf(objects[1]))
                    .attraction(String.valueOf(objects[2]))
                    .description(String.valueOf(objects[3]))
                    .latitude((Double) objects[4])
                    .longitude((Double) objects[5])
                    .tag(String.valueOf(objects[6]))
                    .regionIdx(Long.parseLong(String.valueOf(objects[7])))
                    .path(String.valueOf(objects[8]))
                    .build();
    }

    default BoardDTO.recentBoard recentBoardEntityToDto(Object[] objects) {
        return BoardDTO.recentBoard.builder()
                .boardIdx(Long.parseLong(String.valueOf(objects[0])))
                .title(String.valueOf(objects[1]))
                .regDate(LocalDateTime.parse(objects[2].toString()))
                .nickname((String) objects[3])
                .build();
    }

    default PlanDTO.bestPlan bestPlanEntityToDto(Object[] objects, AttractionImageEntity bestAttrImage) {
        return PlanDTO.bestPlan.builder()
                .planIdx(Long.parseLong(String.valueOf(objects[0])))
                .dateFrom((Date) objects[1])
                .dateTo((Date) objects[2])
                .title(String.valueOf(objects[3]))
                .nickname(String.valueOf(objects[5]))
                .path(bestAttrImage.getPath())
                .build();
    }
}
