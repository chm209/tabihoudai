package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.dto.AttractionDTO;
import com.tabihoudai.tabihoudai_api.dto.BoardDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public interface HomeServices {

    List<AdminDTO.bannerInfo> getBanner();
    List<AttractionDTO.attrPreview> getAttraction(int area, Integer city);
    List<BoardDTO.recentBoard> getBoard();

    default AdminDTO.bannerInfo entityToDto(BannerEntity banner) {
        return AdminDTO.bannerInfo.builder()
                .bannerIdx(banner.getBannerIdx())
                .path(banner.getPath())
                .regDate(banner.getRegDate())
                .build();
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
}
