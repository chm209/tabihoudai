package com.tabihoudai.tabihoudai_api.service;

import com.tabihoudai.tabihoudai_api.dto.AdminDTO;
import com.tabihoudai.tabihoudai_api.dto.AttractionDTO;
import com.tabihoudai.tabihoudai_api.dto.BoardDTO;
import com.tabihoudai.tabihoudai_api.dto.PlanDTO;
import com.tabihoudai.tabihoudai_api.entity.admin.BannerEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.AttractionImageEntity;
import com.tabihoudai.tabihoudai_api.entity.attraction.RegionEntity;
import com.tabihoudai.tabihoudai_api.entity.board.BoardEntity;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import static com.tabihoudai.tabihoudai_api.entity.plan.QPlanEntity.planEntity;
import static com.tabihoudai.tabihoudai_api.entity.plan.QPlanLikeEntity.planLikeEntity;
import static com.tabihoudai.tabihoudai_api.entity.users.QUsersEntity.usersEntity;

public interface HomeServices {
    AdminDTO.bannerInfoResponse getBanner();

    AttractionDTO.attractionInfoResponse getAttraction(int area, Integer city);

    PlanDTO.planInfoResponse getPlan(Integer area);

    BoardDTO.boardInfoResponse getBoard();

    AttractionDTO.regionCityInfoResponse getCity(Integer area);

    default AdminDTO.mainBannerData entityToDto(BannerEntity banner) {
        return AdminDTO.mainBannerData.builder()
                .path(banner.getPath())
                .build();
    }

    default AttractionDTO.mainAttractionData entityToDto(Object[] objects) {
        return AttractionDTO.mainAttractionData.builder()
                .attrIdx(Long.parseLong(String.valueOf(objects[0])))
                .address(String.valueOf(objects[1]))
                .name(String.valueOf(objects[2]))
                .tag(String.valueOf(objects[6]))
                .path(String.valueOf(objects[8]))
                .build();
    }

    default String extractPlanAttrImage(Object[] objects) {
        return String.valueOf(objects[4]);
    }

    default PlanDTO.mainPlanData entityToDto(Object[] objects, AttractionImageEntity attrImage) {
        // 날짜 계산
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        LocalDate dateFrom = LocalDate.parse(String.valueOf(objects[1]));
        LocalDate dateTo = LocalDate.parse(String.valueOf(objects[2]));
        Period period = Period.between(dateFrom, dateTo);
        int diff = period.getDays();
        String date = String.valueOf(diff - 1).concat("泊").concat(String.valueOf(diff)).concat("日");

        return PlanDTO.mainPlanData.builder()
                .planIdx(Long.parseLong(String.valueOf(objects[0])))
                .planImgPath(attrImage.getPath())
                .nickName(String.valueOf(objects[6]))
                .title(String.valueOf(objects[3]))
                .likeCount(Integer.parseInt(String.valueOf(objects[5])))
                .date(date)
                .build();
    }

    default BoardDTO.mainBoardData entityToDto(BoardEntity board) {
        return BoardDTO.mainBoardData.builder()
                .boardIdx(board.getBoardIdx())
                .title(board.getTitle())
                .date(board.getRegDate())
                .nickname(board.getUsersEntity().getNickname())
                .build();
    }

    default AttractionDTO.cityData entityToDto(RegionEntity regions) {
        return AttractionDTO.cityData.builder().city(regions.getCity()).build();
    }
}
